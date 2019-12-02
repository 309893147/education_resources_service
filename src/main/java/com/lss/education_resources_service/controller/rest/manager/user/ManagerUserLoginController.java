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
