package com.education.resources.bean.entity;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.entity.user.User;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
//@ApiModel(value = "Resource", description = "资源表")
@Meta(delete = DeleteType.DELETE_BOTH, edit = EditType.EDIT)
public class Resource extends BaseEntity {

//    @ApiModelProperty(value = "资源链接")
    @Meta(displayInList = true,displayName = "资源链接")
    private String  link;

//    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    @ApiModelProperty(value = "上传人")
    private User user;

//    @ApiModelProperty(value = "处理人 Id")
    private Integer managerId;

    @OneToOne
    @JoinColumn(name = "managerId", insertable = false, updatable = false)
//    @ApiModelProperty(value = "处理人")
    private Manager manager;

//    @ApiModelProperty(value = "标签")
    @Meta(displayInList = true, searchable = true,displayName = "标签")
    private String tag;

//    private  Subject  subject;

//    @ApiModelProperty(value = "资源下载所需积分")
//    private String  downloadIntegral;

//    @ApiModelProperty(value = "标题")
    @Meta(displayInList = true, searchable = true,displayName = "标题")
    private String title;

    @Lob
//    @ApiModelProperty(value = "简介")
    @Meta(displayInList = true, searchable = true,displayName = "简介")
    private String content;

//    @ApiModelProperty(value = "分类Id")
    private Integer basicTypeId;

    @OneToOne
    @JoinColumn(name = "basicTypeId", insertable = false, updatable = false)
    @Meta(displayInList = true,displayName = "分类")
//    @ApiModelProperty(value = "分类")
    private BasicType basicType;

//    @ApiModelProperty(value = "推荐")
    @Meta(displayInList = true, searchable = true,displayName = "推荐")
    private Boolean recommend;

//    @ApiModelProperty(value = "是否显示")
    private Boolean status;

//    @ApiModelProperty(value = "浏览量")
    @Meta(displayInList = true,displayName = "浏览量")
    private Integer clickNumber = 0;

//    @ApiModelProperty(value = "下载量")
    @Meta(displayInList = true,displayName = "下载量")
    private Integer downNumber = 0;

//    private Integer likes = 0;

//    @ApiModelProperty(value = "评分")
    @Meta(displayInList = true,displayName = "评分")
    private Integer score;

//    @ApiModelProperty(value = "路径")
//    private String path;

//    @ApiModelProperty(value = "资源状态")
    @Enumerated(EnumType.STRING)
    @Meta(displayInList = true, searchable = true,displayName = "资源状态")
    private ResourceStatus resourceStatus;

    @Meta(displayInList = true, searchable = true,displayName = "LR || GBDT")
    private String operator;

    public enum ResourceStatus {
//        @ApiModelProperty(value = "待处理")
        UNPROCESSED,
//        @ApiModelProperty(value = "已通过")
        PASS,
//        @ApiModelProperty(value = "未通过")
        NOPASS,
    }

//    public enum Subject{
//        CHINESE,
//        MATHEMATICS,
//        ENGLISH,
//        PHYSICAL,
//        CHEMISTRY,
//        BIOLOGICAL,
//        POLITICAL,
//        HISTORY,
//        GEOGRAPHY,
//        COMPUTER
//    }

}
