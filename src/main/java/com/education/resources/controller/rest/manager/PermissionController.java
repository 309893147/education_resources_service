package com.education.resources.controller.rest.manager;

import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.auth.Menu;
import com.education.resources.bean.auth.Permission;
import com.education.resources.service.auth.PermissionService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Api(tags = "权限管理")
@RequestMapping("/manage/permission")
@PermissionDes(menu = {"权限管理","管理员列表"})
public class PermissionController {

    @Autowired
    PermissionService permissionService;

//    @ApiOperation(value = "获取后台权限")
    @GetMapping("/admin")
    @PermissionDes(name = "编辑权限")
    public API<List<Menu>> getEmployeePermission(Integer id) {
        return API.ok(permissionService.getAllPermission(id,null));
    }

//    @ApiOperation(value = "保存管理员后台权限")
    @PostMapping("/admin")
    @PermissionDes(name = "保存")
    public API saveEmployeePermission(int id, @RequestBody List<Permission> permissions) {
        permissionService.saveManagerPermission(id, permissions);
        return API.ok();
    }
}
