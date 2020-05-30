package com.education.resources.datasource.repository.resource;

import com.education.resources.bean.entity.Resource;
import com.education.resources.datasource.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface ResourceRepository extends BaseRepository<Resource> {

    @Query("select item.id from Resource  item where item.tag like :keyword or item.content like :keyword or item.title like :keyword ")
    Set<Integer> getResourceByKeyWord(@Param("keyword") String keyword);
}
