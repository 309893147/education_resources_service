package com.education.resources.bean.entity;

import com.lss.jpa.entity.BaseEntity;
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
