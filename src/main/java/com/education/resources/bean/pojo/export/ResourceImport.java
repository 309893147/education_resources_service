package com.education.resources.bean.pojo.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ResourceImport {
    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("链接")
    private String link;

    @ExcelProperty("点击数")
    private Integer clickNumber;
    @ExcelProperty("标签1")
    private String tag1;
    @ExcelProperty("标签2")
    private String tag2;
    @ExcelProperty("标签3")
    private String tag3;
    @ExcelProperty("标签4")
    private String tag4;

    @ExcelProperty("类型")
    private String type;

    private String  reason;
}
