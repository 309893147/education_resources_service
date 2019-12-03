package com.lss.education_resources_service.util.respons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页相关类")
public class PageForm {

    @ApiModelProperty(value = "当前页从1开始默认为1")
    private int pageNum =1;

    @ApiModelProperty(value = "每页大小默认为10")
    private int pageSize =10;


}
