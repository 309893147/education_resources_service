package com.lss.education_resources_service.controller.rest.manager.user;


import com.lss.education_resources_service.service.user.ManagerService;
import com.lss.education_resources_service.util.respons.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/manager/user")
@Api(tags = "manager用户登录相关类")
public class ManagerUserLoginController {

    @Autowired
    ManagerService mManagerService;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录传mobile与password")
    public API<HashMap<String, Object>> login(@RequestParam("mobile") String mobile,@RequestParam("password")String password){
        return API.ok(mManagerService.login(mobile,password));
    }

}
