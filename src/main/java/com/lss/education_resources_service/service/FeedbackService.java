package com.lss.education_resources_service.service;

import com.lss.education_resources_service.bean.entity.feedback.Feedback;
import com.lss.education_resources_service.datasource.repository.FeedBackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private FeedBackRepository mFeedbackRepository;

    public FeedbackService(FeedBackRepository mFeedbackRepository) {
        this.mFeedbackRepository = mFeedbackRepository;
    }

    public Feedback editFeedback(Feedback feedback) {
        return (Feedback)this.mFeedbackRepository.save(feedback);
    }
}

