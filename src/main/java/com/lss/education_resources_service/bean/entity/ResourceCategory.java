package com.lss.education_resources_service.bean.entity;

import com.lss.education_resources_service.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@ApiModel(value = "ResourceComment",description = "资源分类表")
public class ResourceCategory extends BaseEntity {
  //  @NotEmpty(message = "名称不能为空")
  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "分类状态")
  private Integer  status = 0;

  @ApiModelProperty(value = "分类图标")
  private String  icon;

//    //附加信息
//    private String  extra;
//
//    @Transient
//    private Map<String,String> extraMap;
}
