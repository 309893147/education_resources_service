package com.education.resources.bean.config;


import com.education.resources.bean.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Data
@Entity
public class Config extends BaseEntity {

    private String name;

    private String title;

    private String description;

    private String value;

    private boolean must;

    private String defaultValue;

    private String  test;

    @Transient
    private String type;

    @Transient
    private List<Config> configs;

}
