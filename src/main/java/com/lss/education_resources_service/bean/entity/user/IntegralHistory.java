package com.lss.education_resources_service.bean.entity.user;


import com.lss.education_resources_service.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@ApiModel(value = "IntegralHistory",description = "积分记录表")
public class IntegralHistory extends BaseEntity {

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "积分编码表")
    private Integer integralCodeId;


}
