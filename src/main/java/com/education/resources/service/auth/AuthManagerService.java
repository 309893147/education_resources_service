package com.education.resources.service.auth;

import com.education.resources.bean.auth.*;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.PageForm;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.datasource.repository.auth.ManagerLoginLogRepository;
import com.education.resources.datasource.repository.auth.ManagerRepository;
import com.education.resources.util.RedisUtil;
import com.education.resources.util.StringUtil;
import com.education.resources.util.TokenUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.education.resources.util.rest.APIError;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AuthManagerService {

    private RedisTemplate<String, ManagerLoginLog> mUserRedisTemplate;

    private PermissionService permissionService;

    private ManagerRepository managerRepository;

    private ManagerLoginLogRepository loginLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public void setmUserRedisTemplate(RedisTemplate redisTemplate) {
        this.mUserRedisTemplate = RedisUtil.createClient(redisTemplate);
    }

    @Autowired
    public void setLoginLogRepository(ManagerLoginLogRepository loginLogRepository) {
        this.loginLogRepository = loginLogRepository;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    /**
     * 管理员登录
     * @param loginForm
     * @return
     */
    public ManagerLoginVO login(LoginForm loginForm) {
        Manager manager = managerRepository.findOne(Specifications.<Manager>and().eq("mobile", loginForm.getMobile()).eq("password", getPassword(loginForm.getPassword())).build()).orElse(null);
        if (manager == null) {
            APIError.e("账号或密码错误");
        }
        ManagerLoginLog loginLog = loginLogRepository.findOne(Specifications.<ManagerLoginLog>and().eq("managerId", manager.getId()).eq("status",ManagerLoginLog.Status.ACTIVE).build()).orElse(null);
        if (loginLog != null) {
            loginLog.setStatus(ManagerLoginLog.Status.EXPIRED);
            loginLogRepository.save(loginLog);
            mUserRedisTemplate.delete(  "manager_" + loginLog.getToken());
        }
        loginLog = new ManagerLoginLog();
        loginLog.setToken(TokenUtil.generateToken());
        loginLog.setManagerId(manager.getId());
        loginLog.setIp(getIP());
        loginLogRepository.save(loginLog);
        //save login token
        String key = "manager_" + loginLog.getToken();
        mUserRedisTemplate.boundValueOps(key).set(loginLog,30, TimeUnit.MINUTES);
        ManagerToken jwtToken = new ManagerToken(loginLog.getToken());
        SecurityUtils.getSubject().login(jwtToken);
        return ManagerLoginVO.builder().token(loginLog.getToken()).permissions(permissionService.getPermissionByUser(manager.getId())).info(manager).build();
    }

    /**
     * 初始化超级管理员
     * @param form
     * @return
     */
    private Manager  initManager(CreateManagerForm  form){
        if (managerRepository.count(Specifications.<Manager>and().eq("type",Manager.Type.SUPER_MANAGER).build()) >0){
            APIError.e("超级管理员已存在");
        };
        Manager manager = new Manager();
        manager.setType(Manager.Type.SUPER_MANAGER);
        manager.setName(form.getName());
        manager.setMobile(form.getMobile());
        manager.setPassword(getPassword(form.getPassword()));
        return managerRepository.save(manager);
    }

    public Manager createUserManager(Integer userId){
        User user = userRepository.findItemById(userId);
        if (user.getMobile()!=null){
            Manager manager = new Manager();
            manager.setType(Manager.Type.USER);
            manager.setName(user.getNickName());
            manager.setMobile(user.getMobile());
            manager.setPassword("123456");
            manager.setKtNumber(""+System.nanoTime());
            return managerRepository.save(manager);
        }
        return  null;
    }

    /**
     * 创建或者编辑管理员
     * @param form
     * @param init
     * @return
     */
    public Manager  createManager(CreateManagerForm form,boolean  init){
        if (init){
            return initManager(form);
        }
        if(form.getId() == null && managerRepository.count(Specifications.<Manager>and().eq("mobile",form.getMobile()).build())>0){
            APIError.e("该登录账号已被注册");
        }
        Manager manager = new Manager();
        if (form.getId() != null){
            manager = managerRepository.findItemById(form.getId());
        }
        if (!form.getMobile().equals(manager.getMobile()) && managerRepository.count(Specifications.<Manager>and().eq("mobile",form.getMobile()).build())>0){
            APIError.e("该登录账号已被注册");
        }
        manager.setMobile(form.getMobile());
        if (manager.getPassword() == null){
            manager.setPassword(getPassword(form.getPassword()));
        }
        manager.setRoles(form.getRoles());
        manager.setType(Manager.Type.MANAGER);
        manager.setName(form.getName());
        return managerRepository.save(manager);
    }

    private String  getPassword(String password){
        return  DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 获取管理员列表
     * @param manager
     * @param pageForm
     * @return
     */
    public Page<Manager> getManager(Manager manager, PageForm pageForm) {
        PredicateBuilder<Manager> spec = SpecificationUtil.filter(manager,pageForm);
        spec.eq("type",Manager.Type.MANAGER);
        return managerRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    /**
     * 更改登录密码
     * @param loginForm
     */
    public void changePassword(PasswordForm loginForm) {
        Manager manager = getCurrentManager();
        manager.setPassword(getPassword(loginForm.getPassword()));
        managerRepository.save(manager);
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前登录管理员
     * @return
     */
    public Manager  getCurrentManager(){
        ManagerLoginLog loginLog = getCurrentUserLogin(true).get();
        return managerRepository.findItemById(loginLog.getManagerId());
    }


    public Optional<ManagerLoginLog> getCurrentUserLogin(boolean throwError){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            if (throwError){
                APIError.NEED_LOGIN();
            }
            return Optional.empty();
        }

        return Optional.ofNullable((ManagerLoginLog) subject.getPrincipal());
    }

    protected HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        return servletRequest;
    }

    private String  getIP(){
        HttpServletRequest request = getCurrentRequest();
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty()) {
            return ip;
        }
        return request.getHeader("remoteAddr");
    }

    /**
     * 删除管理员
     * @param ids
     */
    public void deleteManager(String ids) {
        managerRepository.deleteInBatch(StringUtil.toDeleteBatch(ids,Manager.class));
    }

    /**
     * 从缓存获取当前登录用户
     * @param token
     * @return
     */
    public ManagerLoginLog getManagerLoginLog(String token) {
        String key ="manager_"+token;
        return mUserRedisTemplate.boundValueOps(key).get();
    }
}
