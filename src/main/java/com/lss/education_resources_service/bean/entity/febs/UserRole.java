package com.lss.education_resources_service.bean.entity.febs;

import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 2354394771912648574L;
    /**
     * 用户ID
     */

    private Integer userId;

    /**
     * 角色ID
     */

    private Integer roleId;
}
