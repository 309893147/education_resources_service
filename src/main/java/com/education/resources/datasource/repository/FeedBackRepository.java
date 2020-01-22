package com.education.resources.datasource.repository;

import com.education.resources.bean.entity.feedback.Feedback;
import com.lss.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends BaseRepository<Feedback> {
}
