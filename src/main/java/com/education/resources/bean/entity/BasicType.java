package com.education.resources.bean.entity;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@Meta(delete = DeleteType.DELETE_BOTH,edit = EditType.EDIT)
public class BasicType extends BaseEntity {


//    @ApiModelProperty(value = "名称")
    @Meta(displayInList = true,searchable = true)
    private String name;

//    @ApiModelProperty(value = "排序")
    @Meta(displayInList = true)
    private String sort;

//    @ApiModelProperty(value = "图片")
    private String headImg;

//    @ApiModelProperty(value = "基础类型")
//    @Meta(displayInList = true,searchable = true)
//    @Enumerated(EnumType.STRING)
//    private Type type;
//
//    public enum Type{
//        @ApiModelProperty(value = "公告通知类型")
//        NOTICE,//公告通知类型 notice
//        @ApiModelProperty(value = "资源类型")
//        RESOURCE,
//    }
}
