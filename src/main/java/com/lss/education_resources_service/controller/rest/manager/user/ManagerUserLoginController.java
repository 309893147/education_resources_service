<<<<<<< HEAD
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
=======
//package com.lss.education_resources_service.controller.rest.manager.user;
//
//
//import com.lss.education_resources_service.bean.entity.user.User;
//import com.lss.education_resources_service.framework.redis.RedisCache;
//import com.lss.education_resources_service.service.user.ManagerService;
//import com.lss.education_resources_service.service.user.UserService;
//import com.lss.education_resources_service.util.API;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//
//@RestController
//@RequestMapping("/manager/user")
//public class ManagerUserLoginController {
//
//    @Autowired
//    ManagerService mManagerService;
//
//    @Autowired
//    RedisCache redisService;
//
////    @Value("${spring.redis.timeout}")
////    long expireTokenTime;
//
//    @PostMapping("login")
//    public API<HashMap<String, Object>> login(@RequestParam String username, @RequestParam String password){
//        return API.ok(mManagerService.login(username,password));
//    }
//
//}
>>>>>>> 7e20bb064d305d40054f97300687a0935958cbaf
