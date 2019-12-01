package com.lss.education_resources_service.bean.entity.question;

import io.swagger.annotations.ApiModelProperty;

public class ExaminationPaper {

    //  @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "试卷名称")
    private String title;

    @ApiModelProperty(value = "试卷描述")
    private String description;
}
