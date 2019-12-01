package com.lss.education_resources_service.bean.entity.feedback;

import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Feedback extends BaseEntity {
    private String content;
    private Integer userId;
    private String username;
    private String contact;
}
