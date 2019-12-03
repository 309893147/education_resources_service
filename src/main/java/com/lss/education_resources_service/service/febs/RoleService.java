package com.lss.education_resources_service.service.febs;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.lss.education_resources_service.bean.entity.febs.Role;
import com.lss.education_resources_service.bean.entity.febs.RoleMenu;
import com.lss.education_resources_service.datasource.mapper.febs.RoleMapper;
import com.lss.education_resources_service.datasource.mapper.febs.RoleMenuMapper;
import com.lss.education_resources_service.datasource.mapper.febs.UserRoleMapper;
import com.lss.education_resources_service.exception.APIError;
import com.lss.education_resources_service.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class RoleService  extends BaseService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    public List<Role> queryByPage(Role roleDto) {
        List<Role> roleDtos = roleMapper.queryByPage(roleDto);
        for (Role dto : roleDtos) {
            List<String> menuIds = roleMenuMapper.queryByRoleId(dto.getId());
            String join = StringUtils.join(menuIds, StringPool.COMMA);
            dto.setMenuIds(join);
        }
        return roleDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Map<String, Object> rquestMap) {
        String roleIds = (String) rquestMap.get("roleIds");
        String[] split = roleIds.split(StringPool.COMMA);
        for (String roleId : split) {
            roleMapper.delete(Integer.valueOf(roleId));
            //删除用户与角色关联关系
            userRoleMapper.deleteByRoleId(roleId);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Role role) {
        roleMapper.save(role);
        String memuIds = role.getMenuIds();
        if (StringUtils.isBlank(memuIds)){
            APIError.e(400,"菜单为空");
        }
        String[] split = memuIds.split(StringPool.COMMA);
        for (String menuId : split) {
            RoleMenu roleMenuDto = new RoleMenu();
            roleMenuDto.setMenuId(Integer.valueOf(menuId));
            roleMenuDto.setRoleId(role.getId());
            roleMenuMapper.save(roleMenuDto);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Role role) {
        roleMapper.update(role);
        //删除角色与菜单对应关系
        roleMenuMapper.deleteByRoleId(role.getId());
        String memuIds = role.getMenuIds();
        if (StringUtils.isBlank(memuIds)){
            APIError.e(400,"菜单为空");
        }
        String[] split = memuIds.split(StringPool.COMMA);
        for (String menuId : split) {
            RoleMenu roleMenuDto = new RoleMenu();
            roleMenuDto.setMenuId(Integer.valueOf(menuId));
            roleMenuDto.setRoleId(role.getId());
            roleMenuMapper.save(roleMenuDto);
        }

    }

    public List<Role> findUserRole(Integer userId) {
        List<Role> roleDtos = roleMapper.findUserRole(userId);
        return roleDtos;
    }
}
