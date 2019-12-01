package com.lss.education_resources_service.bean.entity;

import com.lss.education_resources_service.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@ApiModel(value = "ResourceComment",description = "资源点赞表")
public class ResourceApprove extends BaseEntity {

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "资源Id")
    private Integer resourceId;

}
