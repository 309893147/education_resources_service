package com.education.resources.service.auth;


import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.auth.Menu;
import com.education.resources.bean.auth.Permission;
import com.education.resources.bean.auth.Role;
import com.education.resources.bean.from.PageForm;
import com.education.resources.config.auth.AuthResourceConfig;
import com.education.resources.datasource.repository.auth.ManagerRepository;
import com.education.resources.datasource.repository.auth.RoleRepository;
import com.education.resources.util.StringUtil;
import com.education.resources.util.jpa.SpecificationUtil;
import com.education.resources.util.rest.APIError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Service
public class PermissionService{

    private RoleRepository roleRepository;

    private ManagerRepository managerRepository;

    private AuthManagerService  authManagerService;


    @Autowired
    public void setAuthManagerService(AuthManagerService authManagerService) {
        this.authManagerService = authManagerService;
    }

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Page<Role> getRoleList(Role role, PageForm pageForm){
        return roleRepository.findAll(SpecificationUtil.<Role>filter(role,pageForm).build(),pageForm.pageRequest());
    }

    /**
     * 修改编辑角色
     * @param role
     * @return
     */
    public Role editRole(Role role) {

        return roleRepository.save(role);
    }

    /**
     * 获取当前登录管理员的权限
     * @return
     */
    public List<Permission> getSelfPermission(){
        return getPermissionByUser(authManagerService.getCurrentManager().getId());
    }

    /**
     * 根据管理员id获取管理员权限
     * @param managerId
     * @return
     */
    public List<Permission> getPermissionByUser(Integer managerId){
        Manager manager = managerRepository.findItemById(managerId);
        if (manager == null){
            APIError.NOT_FOUND();
        }
        return manager.getRoles().stream().map(new Function<Role, List<Permission>>() {
            @Override
            public List<Permission> apply(Role role) {
                return role.getPermissions();
            }
        }).reduce(new BinaryOperator<List<Permission>>() {
            @Override
            public List<Permission> apply(List<Permission> permissions, List<Permission> permissions2) {
                permissions.addAll(permissions2);
                return permissions;
            }
        }).orElse(new ArrayList<>());
    }

    /**
     * 检查当前登录管理员是否有该功能的权限
     * @param method
     * @param url
     * @return
     */
    public boolean hasPermission(String method, String url) {

        return authManagerService.getCurrentManager().getType().equals(Manager.Type.SUPER_MANAGER) || getSelfPermission().indexOf(new Permission(method,url)) > -1;
    }

    /**
     * 查看所有权限
     * @param manger 管理员id，可选
     * @param role 角色id，可选
     * @return
     */
    public List<Menu> getAllPermission(Integer manger, Integer role) {
        List<Menu> menuList = AuthResourceConfig.menuList;
        checkMyPermission(menuList,null,true);
        if (manger != null){
            List<Permission> permissions = getPermissionByUser(manger);
            checkMyPermission(menuList,permissions,false);
            return menuList;
        }
        if(role != null){
            List<Permission> permissions = roleRepository.findItemById(role).getPermissions();
            checkMyPermission(menuList,permissions,false);
            return menuList;
        }


        return menuList;
    }

    private void checkMyPermission(Collection<Menu> menus, List<Permission> permissions, boolean clear){
        if (menus == null || menus.size() ==0){
            return;
        }
        menus.forEach(it ->{
            it.getPermissions().forEach(pe ->{
                if (clear){
                    pe.setCheck(false);
                }else {
                    pe.setCheck(permissions.indexOf(pe)>-1);
                }

            });
            checkMyPermission(it.getSub(),permissions,clear);
        });
    }

    /**
     * 删除角色
     * @param ids
     */
    public void deleteRole(String ids) {
        roleRepository.deleteInBatch(StringUtil.toDeleteBatch(ids,Role.class));
    }
}
