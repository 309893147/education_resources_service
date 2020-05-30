package com.education.resources.bean.from;

import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BasicType;
import lombok.Data;
import javax.persistence.*;

@Data
@Meta
public class GetResourceFrom {

    private String ids;


    @Meta(displayInList = true,displayName = "资源链接")
    private String  link;

    private Integer userId;

    private Integer managerId;

    @Meta(displayInList = true, searchable = true,displayName = "标签")
    private String tag;

    @Meta(displayInList = true, searchable = true,displayName = "标题")
    private String title;

    @Lob
    @Meta(displayInList = true, searchable = true,displayName = "简介")
    private String content;

    @Meta(displayInList = true, searchable = true,displayName = "标签/标题/简介")
    private String keyword;

    private Integer basicTypeId;

    @OneToOne
    @JoinColumn(name = "basicTypeId", insertable = false, updatable = false)
    @Meta(displayInList = true,displayName = "分类")
//    @ApiModelProperty(value = "分类")
    private BasicType basicType;

    //    @ApiModelProperty(value = "浏览量")
    @Meta(displayInList = true,displayName = "浏览量")
    private Integer clickNumber = 0;

    //    @ApiModelProperty(value = "下载量")
    @Meta(displayInList = true,displayName = "下载量")
    private Integer downNumber = 0;

    //    @ApiModelProperty(value = "评分")
    @Meta(displayInList = true,displayName = "评分")
    private Integer score;


    private String operator;



}
