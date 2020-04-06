//package com.education.resources.bean.entity;
//
//
//import com.education.resources.annotation.Meta;
//import com.education.resources.bean.auth.Manager;
//import com.education.resources.bean.entity.user.User;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//
//@Data
//@Entity
//@Meta(displayName = "工单回复")
//public class TicketReply extends BaseEntity {
//
//    private Integer  ticketId;
//
//    private Integer userId;
//
//    private Integer managerId;
//
//    @OneToOne
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    @Meta(displayInList = true)
//    @ApiModelProperty(value = "回复人")
//    private User user;
//
//    @OneToOne
//    @JoinColumn(name = "managerId", insertable = false, updatable = false)
//    @Meta(displayInList = true)
//    @ApiModelProperty(value = "管理员回复")
//    private Manager manager;
//
//    @Lob
//    private String  content;
//
//    private String images;
//
//    private String path;
//
//}
//
