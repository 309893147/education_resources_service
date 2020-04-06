package com.education.resources.bean.entity;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.entity.user.User;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@ApiModel(value = "ResourceUserDownload", description = "用户资源下载统计表")
@Meta(delete = DeleteType.DELETE_BOTH, edit = EditType.EDIT)
public class ResourceUserDownload extends BaseEntity {

    private Integer resourceId;

    @OneToOne
    @JoinColumn(name = "resourceId", insertable = false, updatable = false)
//    @ApiModelProperty(value = "资源")
    private Resource resource;

//    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    private Integer score;

    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    @ApiModelProperty(value = "下载人")
    private User user;



}
