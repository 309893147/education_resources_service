package com.education.resources.bean.auth;

import com.education.resources.bean.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Permission extends BaseEntity {

    private String method;

    private String name;

    private String url;

    private String path;

    private transient  boolean check;

    public Permission() {
    }

    public Permission(String method, String url) {
        this.method = method;
        this.url = url;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Permission)){
            return false;
        }
        Permission permission = (Permission) obj;
        return (getId()!=null && getId().equals(permission.getId())) || (method.equals(permission.getMethod()) && url.equals(permission.getUrl()));
    }
}
