package com.education.resources.bean.entity.notice;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BaseEntity;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
//@Api(tags = "公告通知")
@Meta(delete = DeleteType.DELETE_BOTH,edit = EditType.EDIT)
public class Notice extends BaseEntity {

//    @ApiModelProperty(value = "标题")
    @Meta(displayInList = true,searchable = true,displayName = "标题")
    private String title;

    @Lob
//    @ApiModelProperty(value = "内容")
    @Meta(displayInList = true,displayName = "内容")
    private String content;

//    private int  readCount = 0;
}
