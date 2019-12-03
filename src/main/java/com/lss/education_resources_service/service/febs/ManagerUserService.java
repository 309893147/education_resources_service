package com.lss.education_resources_service.service.febs;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lss.education_resources_service.bean.entity.febs.Role;
import com.lss.education_resources_service.bean.entity.user.User;
import com.lss.education_resources_service.datasource.mapper.febs.ManagerUserMapper;
import com.lss.education_resources_service.datasource.mapper.febs.RoleMapper;
import com.lss.education_resources_service.datasource.mapper.febs.UserRoleMapper;
import com.lss.education_resources_service.datasource.repository.UserRepository;
import com.lss.education_resources_service.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ManagerUserService extends BaseService {


    @Autowired
    UserRepository mUserRepository;

    @Autowired
    ManagerUserMapper mManagerUserMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    public List<User> queryByPage(Page<User> page){
        List<User> managerUsers = mManagerUserMapper.queryByPage(page);

        for (User user : managerUsers) {
            List<Role> roles = roleMapper.findUserRole(user.getId());
            String[] roleIds = roles.stream().map(Role -> String.valueOf(Role.getId())).toArray(String[]::new);
            String[] roleNames = roles.stream().map(Role -> String.valueOf(Role.getRoleName())).toArray(String[]::new);
            String roleId = org.apache.commons.lang3.StringUtils.join(roleIds, StringPool.COMMA);
            String roleName = org.apache.commons.lang3.StringUtils.join(roleNames, StringPool.COMMA);
            user.setRoleId(Integer.valueOf(roleId));
            user.setRoleName(roleName);
        }
        return managerUsers;
    }

//    public void save(ManagerUserDto managerUserDto) {
//        if (StringUtils.isEmpty(managerUserDto.getRoleId())){
//            APIError.CUSTOM.set(400,"部门或者角色不能为空").expose();
//        }
//        String sysUUID = SysUtils.getSysUUID();
//        managerUserDto.setUserId(sysUUID);
//        managerUserDto.setCreateDate(new Date());
//        managerUserDto.setStatus(ManagerUserDto.STATUS_VALID);
//        managerUserDto.setPassword(ManagerUserDto.DEFAULT_PASSWORD);
//        managerUserMapper.save(managerUserDto);
//        String[] split = managerUserDto.getRoleId().split(StringPool.COMMA);
//        for (String s : split) {
//            UserRoleDto userRoleDto = new UserRoleDto();
//            userRoleDto.setRoleId(s);
//            userRoleDto.setUserId(sysUUID);
//            userRoleMapper.save(userRoleDto);
//        }
//    }
//
//    public void delete(Map<String, Object> requestMap) {
//        String userIds = (String) requestMap.get("userIds");
//        String[] split = userIds.split(StringPool.COMMA);
//        for (String userId : split) {
//            managerUserMapper.delete(userId);
//            userRoleMapper.deleteByUserId(userId);
//        }
//    }
//
//    public void passwordReset(Map<String, Object> requestMap) {
//        String userIds = (String) requestMap.get("userIds");
//        String[] split = userIds.split(StringPool.COMMA);
//        for (String userId : split) {
//            managerUserMapper.updatePassword(ManagerUserDto.DEFAULT_PASSWORD,userId);
//        }
//    }
//
//    public void passwordUpdate(Map<String, Object> requestMap) {
//        String userId = (String) requestMap.get("userId");
//        String oldPassword = (String) requestMap.get("oldPassword");
//        String newPassword = (String) requestMap.get("newPassword");
//        ManagerUserDto managerUserDto = managerUserMapper.queryById(userId);
//        if (managerUserDto==null){
//            APIError.NOT_FOUND.expose();
//        }
//        if (!managerUserDto.getPassword().equals(oldPassword)){
//            APIError.CUSTOM.set(400,"密码错误").expose();
//        }
//        managerUserMapper.updatePassword(newPassword,userId);
//    }

//    public User getUserInfo(Map<String, Object> requestMap) {
//        String userId = (String) requestMap.get("userId");
//        ManagerUserDto managerUserDto = managerUserMapper.queryById(userId);
//        if (managerUserDto==null){
//            APIError.NOT_FOUND.expose();
//        }
//        return managerUserDto;
//    }
//
//    public void update(ManagerUserDto managerUserDto) {
//        managerUserMapper.update(managerUserDto);
//        userRoleMapper.deleteByUserId(managerUserDto.getUserId());
//        String[] split = managerUserDto.getRoleId().split(StringPool.COMMA);
//        for (String s : split) {
//            UserRoleDto userRoleDto = new UserRoleDto();
//            userRoleDto.setRoleId(s);
//            userRoleDto.setUserId(managerUserDto.getUserId());
//            userRoleMapper.save(userRoleDto);
//        }
//    }

    public Object getCurrUser() {
        return super.getCurrentUser();
    }

    public void logout(Integer userId){
        super.refreshCache(userId);
    }

}
