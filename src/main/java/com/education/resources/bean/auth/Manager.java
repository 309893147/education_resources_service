package com.education.resources.bean.auth;

import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@Meta
public class Manager extends BaseEntity {

    @Meta(searchable = true,displayInList = true,displayName = "用户名")
//    @ApiModelProperty(name = "用户名")
    private String name;

//    @ApiModelProperty(name = "登录账号")
    @Meta(searchable = true,displayInList = true,displayName = "登录账号")
    private String mobile;

//    @ApiModelProperty(name = "课堂编号")
//    @Meta(searchable = true,displayInList = true,displayName = "课堂编号")
//    private String ktNumber;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
//    @ApiModelProperty(name = "角色列表")
    private List<Role> roles;


    private Type  type = Type.MANAGER;

    public enum Type{
//        @ApiModelProperty(name = "超级管理员")
        SUPER_MANAGER,

//        @ApiModelProperty(name = "管理员")
        MANAGER,

        USER,
    }



}
