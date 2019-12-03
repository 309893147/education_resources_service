package com.lss.education_resources_service.controller.rest.manager.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lss.education_resources_service.bean.entity.user.User;
import com.lss.education_resources_service.service.febs.ManagerUserService;
import com.lss.education_resources_service.service.febs.RoleService;
import com.lss.education_resources_service.service.user.UserService;
import com.lss.education_resources_service.util.respons.API;
import com.lss.education_resources_service.util.respons.PageForm;
import com.lss.education_resources_service.util.respons.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/user")
@Api(tags = "Manager用户相关类")
public class UserManagerController {
    @Autowired
    UserService userService;


    @Autowired
    ManagerUserService managerUserService;

    @Autowired
    RoleService roleService;


    /**
     * 管理员列表
     */
    @GetMapping("/managerList")
    public API<PageResult<User>> managerList(PageForm pageForm) {
        Page<User> page=new Page<>(pageForm.getPageNum(),pageForm.getPageSize());

        List<User> userList = managerUserService.queryByPage(page);
        return API.ok(PageResult.of(userList,page));
    }

    @GetMapping("/userinfo")
    @ApiOperation(value = "获取当前登录用户")
    public API<User> getCurrUser() {
        return  API.ok(userService.getUser());
    }

<<<<<<< HEAD
=======

>>>>>>> 7e20bb064d305d40054f97300687a0935958cbaf
}
