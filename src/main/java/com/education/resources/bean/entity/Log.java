package com.education.resources.bean.entity;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
@Meta(insertable = false,edit = EditType.NONE,delete = DeleteType.NONE)
public class Log extends BaseEntity {

    @Meta(searchable = true,displayName = "用户名",displayInList = true)
    private String userName;

    @Meta(searchable = true,displayName = "操作类型",displayInList = true)
    private String  operation;

    @Meta(searchable = true,displayName = "内容",displayInList = true)
    private String  content;


    @Meta(searchable = true,displayName = "级别",displayInList = true)
    @Enumerated(value = EnumType.STRING)
    private Level  level;

    @Meta(searchable = true,displayName = "用户IP",displayInList = true)
    private String  ip;


    private Integer  userId;



    @Meta(searchable = true,displayName = "类别",displayInList = false)
    @Enumerated(value = EnumType.STRING)
    private Type  type = Type.SYSTEM;

    public   enum   Type{
        @ApiModelProperty("系统")
        SYSTEM,
        @ApiModelProperty("操作")
        OPERATION
    }


    public enum Level{
        @ApiModelProperty("信息")
        INFO,

        @ApiModelProperty("警告")
        WARNING,

        @ApiModelProperty("错误")
        ERROR
    }
}
