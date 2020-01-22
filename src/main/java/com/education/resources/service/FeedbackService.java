package com.education.resources.service;

import com.education.resources.bean.entity.feedback.Feedback;
import com.education.resources.datasource.repository.FeedBackRepository;
import com.github.wenhao.jpa.PredicateBuilder;
import com.lss.bean.form.PageForm;
import com.lss.jpa.specification.SpecificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedBackRepository mFeedbackRepository;


    public Feedback editFeedback(Feedback feedback) {
        return mFeedbackRepository.save(feedback);
    }

    public Page<Feedback> list(Feedback reminder, PageForm pageForm) {
        PredicateBuilder<Feedback> spec = SpecificationUtil.filter(reminder, pageForm);
        return mFeedbackRepository.findAll(spec.build(),pageForm.pageRequest());
    }
}

