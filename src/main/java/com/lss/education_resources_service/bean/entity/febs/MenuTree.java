package com.lss.education_resources_service.bean.entity.febs;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.lss.education_resources_service.util.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class MenuTree<T> extends BaseEntity {

    private static final long serialVersionUID = 7681873362531265829L;

    private String icon;
    private String href;
    private String title;
    private Map<String, Object> state;
    private boolean checked = false;
    private Map<String, Object> attributes;
    private List<MenuTree<T>> childs = new ArrayList<>();
    private Integer parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;

    private Menu data;
}
