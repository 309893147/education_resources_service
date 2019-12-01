package com.lss.education_resources_service.bean.entity.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "QuestionBank",description = "题库表")
public class QuestionBank {

    //  @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "题目名称")
    private String title;

    @ApiModelProperty(value = "题目描述")
    private String description;


}
