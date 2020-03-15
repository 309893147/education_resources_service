package com.education.resources.datasource.repository.resource;

import com.education.resources.bean.entity.Resource;
import com.education.resources.datasource.repository.BaseRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ResourceRepository extends BaseRepository<Resource> {
}
