package com.education.resources.bean.vo;

import lombok.Data;

//首页数据统计
@Data
public class IndexCount {

    private Integer userTotalCount=0;

    private Integer newUserCount=0;
    //未处理资源
    private Integer unResourceCount=0;

    //新增资源
    private Integer todayResourceCount=0;

    //今日处理资源
    private Integer todayNewCount=0;




}
