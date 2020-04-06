package com.education.resources.bean.entity;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.user.User;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
//@ApiModel(value = "ManagerApply", description = "后台管理申请列表")
@Meta(delete = DeleteType.NONE, edit = EditType.NONE,insertable = false)
public class ManagerApply extends BaseEntity{

    private Integer userId;

    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    @ApiModelProperty(value = "用户")
    @Meta(displayInList = true)
    private User user;

    private String mobile;

    private String password;

//    @ApiModelProperty(value = "申请理由")
    @Meta(displayInList = true)
    private String reason;
}
