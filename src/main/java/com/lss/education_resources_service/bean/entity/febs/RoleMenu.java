package com.lss.education_resources_service.bean.entity.febs;

import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class RoleMenu  extends BaseEntity {
    private static final long serialVersionUID = -5200596408874170216L;
    /**
     * 角色ID
     */

    private Integer roleId;

    /**
     * 菜单/按钮ID
     */

    private Integer menuId;

}
