package com.education.resources.bean.entity;

import com.lss.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
@Data
@Entity
@ApiModel(value = "Resource",description = "资源表")
public class Resource extends BaseEntity {

    @ApiModelProperty(value = "资源链接")
    private String  link;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "资源链接密码")
    private String  linkPassword;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "标题")
    private String  title;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "正文")
    private String  content;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "分类Id")
    private Integer resourceCategoryId;

    @ApiModelProperty(value = "是否推荐")
    private Integer recommend = 0;

    @ApiModelProperty(value = "状态")
    private Integer status = 0;

    @ApiModelProperty(value = "浏览量")
    private Integer  view = 0;

    @Transient
    @ApiModelProperty(value = "评论量")
    private Integer commentCount;

    @ApiModelProperty(value = "点赞量")
    private Integer likes = 0;

    @ApiModelProperty(value = "评分")
    private Integer score = 0;

}
