package com.lss.education_resources_service.bean.entity.febs;

import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Role extends BaseEntity {



    /**
     * 角色名称
     */

    private String roleName;

    /**
     * 角色描述
     */

    private String remark;



    /**
     * 角色对应的菜单（按钮） id
     */
    private  String menuIds;
}
