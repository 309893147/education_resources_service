package com.education.resources.bean.entity;

import com.lss.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
@Data
@Entity
@ApiModel(value = "Score",description = "评分表")
public class Score extends BaseEntity {



    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "用户id")
    private String  userId;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "资源id")
    private String  resourceId;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "题库id")
    private String  questionBankId;



}
