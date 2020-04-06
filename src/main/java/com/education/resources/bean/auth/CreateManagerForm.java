package com.education.resources.bean.auth;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateManagerForm {

    private Integer id;

//    @ApiModelProperty(name = "登录账号")
    private String mobile;

//    @ApiModelProperty(name = "账号名称")
    private String name;

//    @ApiModelProperty(name = "登录密码")
    private String password;

//    @ApiModelProperty(name = "用户角色")
    private List<Role> roles;

}
