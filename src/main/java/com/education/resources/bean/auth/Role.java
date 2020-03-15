package com.education.resources.bean.auth;


import com.education.resources.annotation.DeleteType;
import com.education.resources.annotation.EditType;
import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@Meta(delete = DeleteType.DELETE,edit = EditType.EDIT)
public class Role extends BaseEntity {


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;

    @Meta(displayName = "角色名称",displayInList = true,searchable = true,clickable = true)
    private String name;
}
