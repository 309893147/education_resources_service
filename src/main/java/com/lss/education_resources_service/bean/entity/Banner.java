package com.lss.education_resources_service.bean.entity;

import com.lss.education_resources_service.util.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * Created by lewis on 2018/10/12.
 * banner
 */
@Entity
public class Banner  extends BaseEntity {

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "轮播标题")
    private String  title;

    @NotEmpty(message = "请设置图片")
    @ApiModelProperty(value = "轮播图片")
    private String  img;

    @ApiModelProperty(value = "轮播类型")
    private Integer type;

    @ApiModelProperty(value = "轮播跳转路径")
    private String  target;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer  sort;
}
