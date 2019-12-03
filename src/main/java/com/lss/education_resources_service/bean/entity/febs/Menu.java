package com.lss.education_resources_service.bean.entity.febs;

import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Menu extends BaseEntity {

    // 菜单
    public static final String TYPE_MENU = "0";
    // 按钮
    public static final String TYPE_BUTTON = "1";

    public static final Long TOP_NODE = 0L;


    /**
     * 上级菜单ID
     */
    private Integer parentId;

    /**
     * 菜单/按钮名称
     */

    private String menuName;

    /**
     * 菜单URL
     */

    private String url;

    /**
     * 权限标识
     */

    private String perms;

    /**
     * 图标
     */

    private String icon;

    /**
     * 类型 0菜单 1按钮
     */

    private String type;

    /**
     * 排序
     */

    private Long orderNum;




}
