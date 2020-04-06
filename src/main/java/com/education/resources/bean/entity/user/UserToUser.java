//package com.education.resources.bean.entity.user;
//
//
//import com.education.resources.bean.entity.BaseEntity;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Data
//@Entity
//@ApiModel(value = "UserToUser",description = "用户关注表")
//public class UserToUser extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @ApiModelProperty(value = "用户关注id")
//    private Integer userToUserId;
//
//
//    @ApiModelProperty(value = "我的id")
//    private Integer fromUserId;
//
//
//    @ApiModelProperty(value = "被关注人id")
//    private Integer toUserId;
//
//
//    @ApiModelProperty(value = "状态/1单向2双向3拉黑")
//    private  Integer relType;
//
//}
