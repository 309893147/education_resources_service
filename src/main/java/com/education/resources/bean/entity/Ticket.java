package com.education.resources.bean.entity;



import com.education.resources.annotation.Meta;
import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@Meta(displayName = "工单实体")
public class Ticket extends BaseEntity {

    private Integer  userId;

    private Integer managerId;

    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @Meta(displayInList = true)
    @ApiModelProperty(value = "发起人")
    private User user;

    @OneToOne
    @JoinColumn(name = "managerId", insertable = false, updatable = false)
    @Meta(displayInList = true)
    @ApiModelProperty(value = "处理人")
    private Manager manager;


    @ApiModelProperty("处理状态")
    @Meta(displayName = "处理状态",searchable = true,displayInList = true)
    @Enumerated(EnumType.STRING)
    private Status  status;


    @ApiModelProperty("关闭原因")
    private String  closeReason;

    @Lob
    private String  content;

    private String images;

    @Meta(displayName = "完成时间")
    private Timestamp finishTime;

    private String path;


    /**
     * 状态
     */
    public enum Status{

        @Meta(displayName = "未受理")
        CREATED,

        @Meta(displayName = "异常关闭")
        CLOSED,

        @Meta(displayName = "已完成")
        FINISH,

        @Meta(displayName = "已取消")
        CANCELED,

        @Meta(displayName = "已评价")
        RATED

    }

}

