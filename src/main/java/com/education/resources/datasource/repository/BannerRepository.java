package com.education.resources.datasource.repository;

import com.education.resources.bean.entity.Banner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends  BaseRepository<Banner>{

    @Query(value = "select item.* from Banner item WHERE item.join_us = 1 and item.presence_status=1 ORDER BY item.id DESC LIMIT 0,3 ",nativeQuery = true)
    List<Banner> queryBannerLimit();
}
