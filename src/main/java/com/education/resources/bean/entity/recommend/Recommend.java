package com.education.resources.bean.entity.recommend;

import com.education.resources.bean.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Recommend {
    @Id
    private Integer id;

    private Integer recommend;
}
