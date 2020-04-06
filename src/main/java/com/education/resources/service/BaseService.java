package com.education.resources.service;


import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.vo.UserLoginVo;
import com.education.resources.framework.redis.RedisCache;
import com.education.resources.service.auth.AuthManagerService;
import com.education.resources.service.config.ConfigService;
import com.education.resources.util.rest.APIError;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class BaseService  implements ApplicationContextAware {

    protected AuthManagerService authManagerService;

    protected ConfigService configService;

    protected ApplicationContext mContext;

    @Autowired
    RedisCache redisCache;

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public <T> T getConfig(Class<T> clazz){
        return configService.getConfig(clazz);
    }

    @Autowired
    public void setAuthManagerService(AuthManagerService authManagerService) {
        this.authManagerService = authManagerService;
    }

    /**
     * 获取当前登录的管理员
     * @return
     */
    protected Manager getCurrentManager(){
        return authManagerService.getCurrentManager();
    }


    public User getCurrentUser() {
        String token = (String) getHeader("accessToken");
        User user = redisCache.getCacheObject("userToken_" + token);
        if (user == null){
            APIError.NEED_LOGIN();
        }
        return user;
    }

    public void updateCurrentUser(User user) {
        String token = (String) getHeader("accessToken");
        redisCache.setCacheObject("userToken_" + token, user);
    }


    public Object getHeader(String key) {
        return getCurrentRequest().getHeader(key);
    }

    protected HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        return servletRequest;
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.mContext = applicationContext;
    }

    public void sendEvent(Object object){
        mContext.publishEvent(object);
    }

    protected String getIP(){
        return authManagerService.getIP();
    }

}
