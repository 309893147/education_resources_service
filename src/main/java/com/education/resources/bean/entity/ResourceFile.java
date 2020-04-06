//package com.education.resources.bean.entity;
//
//import com.education.resources.annotation.DeleteType;
//import com.education.resources.annotation.Meta;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.persistence.Entity;
//
//@Data
//@Entity
//@Meta(delete = DeleteType.DELETE_BOTH)
//@Api(tags = "文件存放索引")
//public class ResourceFile extends  BaseEntity{
//
//    @ApiModelProperty(value = "文件名")
//    private String name;
//
//    @ApiModelProperty(value = "文件对应的key")
//    private String property;
//
//    @ApiModelProperty(value = "文件路径")
//    private String path;
//
//    @ApiModelProperty(value = "拥有人Id,如房屋Id")
//    private Integer ownerId;
//
//    @ApiModelProperty(value = "类型前缀")
//    private String prefix;
//
//    @ApiModelProperty(value = "文件类型")
//    private String mime;
//
//
//}
