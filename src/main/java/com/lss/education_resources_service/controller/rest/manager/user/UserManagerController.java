package com.lss.education_resources_service.controller.rest.manager.user;

import com.lss.education_resources_service.annotation.AnonUrl;
import com.lss.education_resources_service.service.user.UserService;
import com.lss.education_resources_service.util.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manager/user")
@Api(tags = "Manager用户相关类")
public class UserManagerController {
    @Autowired
    UserService userService;


    @AnonUrl
    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录传userName与password")
    public API<Map<String,Object>> login(@RequestParam("username")String username, @RequestParam("password")String password){
        HashMap<String,Object> map = userService.login(username,password);
        return API.ok(map);
    }
}
