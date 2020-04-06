package com.education.resources.datasource.repository;

import com.education.resources.bean.entity.notice.Notice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoticeRepository extends BaseRepository<Notice> {
//
//    @Modifying
//    @Transactional
//    @Query("update Notice item set item.readCount = item.readCount+1 where item.id=:id")
//    void updateReadCount(@Param("id") Integer id);
}
