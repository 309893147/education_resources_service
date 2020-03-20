package com.education.resources.bean.entity;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

/**
 * Created by lewis on 2018/10/12.
 * banner
 */
@Entity
@Data
@Meta(delete = DeleteType.DELETE_BOTH,edit = EditType.EDIT)
public class Banner extends BaseEntity {

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "轮播标题")
    @Meta(displayInList = true)
    private String title;

    @NotEmpty(message = "请设置图片")
    @ApiModelProperty(value = "轮播图片")
    @Meta(displayInList = true)
    private String headImg;

    @NotEmpty(message = "请输入内容")
    @ApiModelProperty(value = "内容")
    @Lob
    @Meta(displayInList = true)
    private String content;

    @ApiModelProperty(value = "轮播类型")
    @Enumerated(EnumType.STRING)
    @Meta(displayInList = true)
    private Type type;

    @ApiModelProperty(value = "轮播跳转路径")
    @Meta(displayInList = true)
    private String target;

    @ApiModelProperty(value = "是否启用")
    @Meta(displayInList = true)
    private boolean joinUse;

    public enum Type {
        VIDEO,
        IMG
    }


}
