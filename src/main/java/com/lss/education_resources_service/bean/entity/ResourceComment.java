package com.lss.education_resources_service.bean.entity;

import com.lss.education_resources_service.bean.entity.user.User;
import com.lss.education_resources_service.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@ApiModel(value = "ResourceComment",description = "资源评论表")
public class ResourceComment extends BaseEntity {

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "分类图标")
    private Integer  depth = 0;

    @ApiModelProperty(value = "评论父Id")
    private Integer parent;

    @ApiModelProperty(value = "用户")
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id",insertable = false,updatable = false)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    @ApiModelProperty(value = "评论")
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent",referencedColumnName = "id",insertable = false,updatable = false)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<ResourceComment> sub;

    @ApiModelProperty(value = "资源id")
    private Integer resourceId;

    @ApiModelProperty(value = "资源")
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(name = "resourceId",referencedColumnName = "id",insertable = false,updatable = false)
    private Resource  resource;
}
