package com.education.resources.bean.entity.common;

import com.education.resources.bean.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Data
@Entity
public class VerifyCode extends BaseEntity {

    private String  template;

    private String  target;

    private String  content;

    private String code;

    private Timestamp  expire;

}
