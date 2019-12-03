package com.lss.education_resources_service.controller.rest.manager.user;

import com.lss.education_resources_service.bean.entity.febs.Menu;
import com.lss.education_resources_service.bean.entity.febs.MenuTree;
import com.lss.education_resources_service.service.febs.MenuService;
import com.lss.education_resources_service.util.respons.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping("/add")
    public API addMenu(@RequestBody Menu menu){
        menuService.save(menu);
        return API.ok();
    }


    @PostMapping("/edit")
    public API deit(@RequestBody Menu menu){
        menuService.edit(menu);
        return API.ok();
    }

    /**
     * 删除菜单
     * @param requestBody
     * @return
     */
//    @PostMapping("/delete")
//    public String delete(@RequestBody String requestBody){
//        Map<String,Object> requestMap = super.getRequestBody(requestBody);
//        menuService.delete(requestMap);
//        return successResult();
//    }


    /**
     * 查询树菜单
     * @param menuDto
     * @return
     */
    @GetMapping("/tree")
    public API<MenuTree<Menu>> getMenuTree(Menu menu){
        MenuTree<Menu> menus = menuService.findMenus(menu);
        return API.ok(menus);

    }


}
