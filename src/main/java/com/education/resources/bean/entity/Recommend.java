package com.education.resources.bean.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@Entity
public class Recommend extends  BaseEntity{

    @Lob
    private String recommend;

}
