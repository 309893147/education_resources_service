package com.education.resources.bean.entity.user;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BaseEntity;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Entity;

@Data
@Entity
@Meta(delete = DeleteType.NONE,edit = EditType.NONE)
public class User extends BaseEntity {

    private String openId;

    private String avatarUrl;

    @Meta(displayInList = true)
    private String nickName;

//    @ApiModelProperty(value = "性别")
    @Meta(displayInList = true)
    private Integer gender;

//    @ApiModelProperty(value = "城市")
    @Meta(displayInList = true)
    private String city;

//    @ApiModelProperty(value = "国家")
    @Meta(displayInList = true)
    private String country;


//    @ApiModelProperty(value = "省市")
    private String province;


//    @ApiModelProperty(value = "邮箱")
    @Meta(displayInList = true)
    private String email;

//    @ApiModelProperty(value = "手机号")
    @Meta(displayInList = true)
    private String mobile;

//    @ApiModelProperty(value = "年龄")
    private String age;

//    @ApiModelProperty(value = "年级")
    private Integer grade;

//    @ApiModelProperty(value = "我的积分")
    private Integer integral=3;

    private String token;

}
