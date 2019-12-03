package com.lss.education_resources_service.util.respons;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页参数")
public class PageParameter {
    @ApiModelProperty(value = "当前页")
    private Long pageNum;
    @ApiModelProperty(value = "每页大小")
    private Long pageSize;
    @ApiModelProperty(value = "总条数")
    private Long total;
    @ApiModelProperty(value = "总页数")
    private Long pages;

    public static PageParameter setPageParameter(Page page){
        PageParameter pageParameter=new PageParameter();
        pageParameter.setPageNum(page.getCurrent());
        pageParameter.setPageSize(page.getSize());
        pageParameter.setTotal(page.getTotal());
        pageParameter.setPages(page.getPages());
        return pageParameter;
    }
}
