package com.education.resources.bean.entity.feedback;

import com.lss.jpa.entity.BaseEntity;
import com.lss.meta.annotation.DeleteType;
import com.lss.meta.annotation.EditType;
import com.lss.meta.annotation.Meta;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
@Api(tags = "用户反馈")
@Meta(delete = DeleteType.DELETE_BOTH,edit = EditType.EDIT)
public class Feedback extends BaseEntity {
    @ApiModelProperty(value = "内容")
    @Meta(displayInList = true,searchable = true)
    private String content;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
}
