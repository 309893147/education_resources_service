//package com.education.resources.bean.entity.feedback;
//
//import com.education.resources.annotation.DeleteType;
//import com.education.resources.annotation.EditType;
//import com.education.resources.annotation.Meta;
//import com.education.resources.bean.entity.BaseEntity;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import javax.persistence.Entity;
//import javax.persistence.Lob;
//
//@Entity
//@Data
//@Api(tags = "用户反馈")
//@Meta(delete = DeleteType.DELETE_BOTH,edit = EditType.EDIT)
//public class Feedback extends BaseEntity {
//    @Lob
//    @ApiModelProperty(value = "内容")
//    @Meta(displayInList = true,searchable = true)
//    private String content;
//    @ApiModelProperty(value = "用户ID")
//    private Integer userId;
//}
