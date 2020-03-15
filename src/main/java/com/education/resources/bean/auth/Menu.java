package com.education.resources.bean.auth;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Menu {

    private String name;

    private Set<Menu> sub;

    private List<Permission> permissions;


    private String path;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Menu menu = (Menu) object;
        return menu.getPath() != null && menu.getPath().equals(this.getPath());
    }
}
