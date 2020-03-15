package com.education.resources.bean.entity.user;

import com.education.resources.bean.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@ApiModel(value = "UserCollect",description = "用户收藏表")
public class UserCollect extends BaseEntity {


    @ApiModelProperty(value = "类别标题")
    private String title;

    @ApiModelProperty(value = "类别图片")
    private String icon;

    @ApiModelProperty(value = "类别顺序")
    private Integer sort;

}
