package com.education.resources.controller.auth;

import com.education.resources.annotation.AnonUrl;
import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.auth.*;
import com.education.resources.bean.entity.ManagerApply;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.auth.AuthManagerService;
import com.education.resources.service.auth.PermissionService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/manage/manager")
//@Api(tags = "管理员")
public class AuthManagerAPI {

    private AuthManagerService userManagerService;

    private PermissionService permissionService;


    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setUserManagerService(AuthManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }


    @AnonUrl
    @PostMapping("/login")
//    @ApiOperation("管理员登录")
    public API<ManagerLoginVO> login(@RequestBody @Valid LoginForm loginForm){

        return API.ok(userManagerService.login(loginForm));
    }

    @AnonUrl
    @PostMapping("/init")
//    @ApiOperation("初始化超级管理员")
    public API firstRegister(@RequestBody @Valid CreateManagerForm loginForm){
        userManagerService.createManager(loginForm,true);
        return API.ok();
    }

    @PostMapping("/pass")
//    @ApiOperation("修改登录密码")
    public API<ManagerLoginVO>  changeManagerPassword(@RequestBody @Valid PasswordForm loginForm){
        userManagerService.changePassword(loginForm);
        return API.ok();
    }

//    @ApiOperation("创建用户后台账号")
//    @PostMapping(value = "/user")
//    public API<Manager>  createUserManager(Integer userId){
//        Manager userManager = userManagerService.createUserManager(userId);
//        if (userManager==null) {
//            return API.e("请完善个人资料电话号码,后台账号将根据电话号码为用户名");
//        }
//        return API.ok(userManager);
//    }


//    @ApiOperation("创建管理员")
    @PostMapping
    public API<Manager>  createManager(@RequestBody @Valid CreateManagerForm createManagerForm){
        return API.ok(userManagerService.createManager(createManagerForm,false));
    }

//    @ApiOperation("创建管理员")
    @PostMapping(value = "/user")
    public API<Manager>  createUserManager(@RequestBody @Valid CreateManagerForm createManagerForm){
        return API.ok(userManagerService.createUserManager(createManagerForm,false));
    }



//    @ApiOperation("获取管理员列表")
    @GetMapping
    @PermissionDes(menu = {"权限管理","管理员列表"},rewriteMenu = true,name = "显示")
    public API<Page<Manager>>  getManager(Manager manager, PageForm pageForm){
        return API.ok(userManagerService.getManager(manager,pageForm));
    }


    @GetMapping(value = "apply")
//    @ApiOperation(value = "用户列表")
    @PermissionDes(menu = {"权限管理","用户申请管理员列表"},rewriteMenu = true,name = "显示")
    public API<Page<ManagerApply>> getManagerApply(ManagerApply managerApply, PageForm pageForm) {
        return API.ok(userManagerService.getManagerApply(managerApply, pageForm));
    }

//    @ApiOperation("删除管理员")
    @DeleteMapping()
    @PermissionDes(menu = {"权限管理","管理员列表"},rewriteMenu = true,name = "删除")
    public API  deleteManager(@RequestParam String ids){
        userManagerService.deleteManager(ids);
        return API.ok();
    }

//    @ApiOperation("获取管理员meta信息")
    @GetMapping("/apply/meta")
    public API<MetaData> getManagerApplyMeta(){
        return API.ok(MetaUtils.getMeta(ManagerApply.class));
    }

//    @ApiOperation("获取管理员meta信息")
    @GetMapping("/meta")
    public API<MetaData> getManagerMeta(){
        return API.ok(MetaUtils.getMeta(Manager.class));
    }

    @AnonUrl
//    @ApiOperation("获取当前登录用户的权限")
    @GetMapping("/permission")
    public API<List<Permission>> getUserPermission(){

        return API.ok(permissionService.getSelfPermission());
    }




}
