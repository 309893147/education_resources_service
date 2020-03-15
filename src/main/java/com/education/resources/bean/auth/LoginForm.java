package com.education.resources.bean.auth;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("登录参数对象")
public class LoginForm {



    private String mobile;


    @NotNull(message = "密码不能为空")
    private String password;

}
