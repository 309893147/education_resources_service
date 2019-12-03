package com.lss.education_resources_service.service.febs;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.lss.education_resources_service.bean.entity.febs.Menu;
import com.lss.education_resources_service.bean.entity.febs.MenuTree;
import com.lss.education_resources_service.datasource.mapper.febs.MenuMapper;
import com.lss.education_resources_service.datasource.mapper.febs.RoleMenuMapper;
import com.lss.education_resources_service.exception.APIError;
import com.lss.education_resources_service.service.BaseService;
import com.lss.education_resources_service.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MenuService  extends BaseService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;


    public void save(Menu menu) {
        if(menu.getParentId() == null){
            menu.setParentId(0);
        }
        menuMapper.save(menu);
    }


    public void edit(Menu menuDto) {
        if (StringUtils.isEmpty(menuDto.getType())) {
            APIError.NOTEMPTY();
        }
        //为菜单
        if ("0".equals(menuDto.getType())) {
            menuMapper.updateMenu(menuDto);
            //为按钮
        } else if ("1".equals(menuDto.getType())) {
            menuMapper.updateButton(menuDto);
        }
    }

    public void delete(Map<String, Object> requestMap) {
        String menuIds = (String) requestMap.get("menuIds");
        String[] split = menuIds.split(StringPool.COMMA);
        for (String menuId : split) {
            Set<String> menuSetIds = new HashSet<>();
            menuSetIds.add(menuId);
            findSubNode(menuSetIds,menuId);
            menuMapper.delete(menuSetIds);
            //删除角色关联的菜单
            roleMenuMapper.deleteByMenuIds(menuSetIds);

        }
    }
    private void findSubNode(Set<String> deleteIds, String menuId) {
        List<String> setDb = menuMapper.getSonMenu(menuId);
        for (String s : setDb) {
            deleteIds.add(s);
            findSubNode(deleteIds,s);
        }
    }

    public MenuTree<Menu> findMenus(Menu menu) {
        List<Menu> menus =  menuMapper.findMenus(menu);
        List<MenuTree<Menu>> menuDtoMenuTree = this.convertMenus(menus);
        return TreeUtil.buildMenuTree(menuDtoMenuTree);

    }

    private List<MenuTree<Menu>> convertMenus(List<Menu> menus) {
        List<MenuTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree<Menu> tree = new MenuTree<>();
            tree.setId(menu.getId());
            tree.setParentId(menu.getParentId());
            tree.setTitle(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getUrl());
            tree.setData(menu);
            trees.add(tree);
        });
        return trees;
    }

//    public List<Menu> findUserPermissions(String userId) {
//        List<Menu> menuDtos = menuMapper.findUserPermissions(userId);
//        return menuDtos;
//    }
}
