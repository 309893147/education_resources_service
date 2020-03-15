package com.education.resources.bean.entity.user;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

@Data
@Entity
@Meta(delete = DeleteType.NONE,edit = EditType.NONE)
public class User extends BaseEntity {

    private String openId;

    private String avatarUrl;

    @Meta(displayInList = true)
    private String nickName;

    @ApiModelProperty(value = "性别")
    @Meta(displayInList = true)
    private Integer gender;

    @ApiModelProperty(value = "生日")
    @Meta(displayInList = true)
    private Timestamp birthday;

    @ApiModelProperty(value = "城市")
    @Meta(displayInList = true)
    private String city;

    @ApiModelProperty(value = "国家")
    @Meta(displayInList = true)
    private String country;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "省市")
    private String province;

    @Column(unique = true,length =15)
    @ApiModelProperty(value = "电话")
    @Meta(displayInList = true)
    private String mobile;

    @ApiModelProperty(value = "qq")
    @Meta(displayInList = true)
    private String qq;

    @ApiModelProperty(value = "邮箱")
    @Meta(displayInList = true)
    private String email;

    @ApiModelProperty(value = "年龄")
    private String age;


    @ApiModelProperty(value = "剩余积分")
    private Integer integral;

    @ApiModelProperty(value = "总积分")
    private Integer integralTotal;

//    /**
//     * 角色 ID
//     */
//    @ApiModelProperty(value = "角色id")
//    private Integer roleId;
//
//
//    private String roleName;

}
