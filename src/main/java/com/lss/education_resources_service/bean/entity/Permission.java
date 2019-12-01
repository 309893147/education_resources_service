package com.lss.education_resources_service.bean.entity;

import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by lewis on 2018/11/7.
 *
 */
@Data
@Entity
public class Permission extends BaseEntity {
    private Integer  userId;
    private String permissions;
}
