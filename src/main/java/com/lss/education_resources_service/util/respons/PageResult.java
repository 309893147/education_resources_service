package com.lss.education_resources_service.util.respons;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "分页后的列表",description = "分页后的列表")
public class PageResult<T> {
    @ApiModelProperty(value = "数据列表",required = true)
    private List<T> list;
    @ApiModelProperty(value = "分页参数")
    private PageParameter page;

    public static <T> PageResult<T> of(List<T> list,Page page){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setPage(PageParameter.setPageParameter(page));
        return pageResult;
    }
}
