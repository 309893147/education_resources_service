package com.education.resources.bean.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lss.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

@Data
@Entity
@JsonIgnoreProperties({"updateTime","password"})
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "生日")
    private Timestamp birthday;

    @ApiModelProperty(value = "城市")
    private String city;

    @Column(unique = true,length =15)
    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;


    @ApiModelProperty(value = "剩余积分")
    private Integer integral;

    @ApiModelProperty(value = "总积分")
    private Integer integralTotal;

    /**
     * 角色 ID
     */
    @ApiModelProperty(value = "角色id")
    private Integer roleId;


    private String roleName;

}
