package com.education.resources.controller.rest.manager.user;


import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.entity.ManagerApply;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.UserService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/user")
@Api(tags = "Manager用户相关类")
public class UserController {

    @Autowired
    UserService userService;


    @ApiOperation(value = "配置元信息")
    @GetMapping("/meta")
    public API<MetaData> userMeta() {
        return API.ok(MetaUtils.getMeta(User.class));
    }

    @GetMapping
    @ApiOperation(value = "用户列表")
    @PermissionDes(menu = {"用户列表"}, name = "显示")
    public API<Page> getUserList(User user, PageForm pageForm) {
        return API.ok(userService.getUserList(user, pageForm));
    }

    @GetMapping(value = "detail")
    @ApiOperation(value = "用户详情")
    public API<User> getOne(Integer id) {
        return API.ok(userService.getOne(id));
    }




}
