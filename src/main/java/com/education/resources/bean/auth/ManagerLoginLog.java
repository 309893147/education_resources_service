package com.education.resources.bean.auth;

import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;


@Entity
@Data
@ApiModel("登录信息")
@Meta(insertable = false,edit = EditType.NONE,delete = DeleteType.NONE)
public class ManagerLoginLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3266088548079059115L;

    @ApiModelProperty(value = "登录token")
    private String token;

    @ApiModelProperty(value = "管理员id")
    private Integer managerId;

    @ApiModelProperty(value = "用户名")
    @Meta(searchable = true,displayName = "用户名",displayInList = true)
    private String name;

    @ApiModelProperty(value = "登录ip")
    @Meta(searchable = true,displayName = "用户IP",displayInList = true)
    private String  ip;


    private Status  status = Status.ACTIVE;

    public enum Status{
        ACTIVE,
        DISABLED,
        DELETED,
        EXPIRED
    }

}
