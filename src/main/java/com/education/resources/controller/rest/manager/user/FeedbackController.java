package com.education.resources.controller.rest.manager.user;

import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.entity.feedback.Feedback;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.FeedbackService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/feedback")
@Api(tags = "Manager用户相关类")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @ApiOperation(value = "配置元信息")
    @GetMapping("/meta")
    public API<MetaData> userMeta() {
        return API.ok(MetaUtils.getMeta(Feedback.class));
    }


    @GetMapping
    @ApiOperation(value = "用户反馈列表")
    @PermissionDes(menu = {"用户反馈"}, name = "显示")
    public API<Page> complaintList(Feedback feedback, PageForm pageForm) {
        return API.ok(feedbackService.list(feedback, pageForm));
    }

}
