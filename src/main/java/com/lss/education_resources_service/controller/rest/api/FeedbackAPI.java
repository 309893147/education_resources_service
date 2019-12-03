package com.lss.education_resources_service.controller.rest.api;


import com.lss.education_resources_service.bean.entity.feedback.Feedback;
import com.lss.education_resources_service.bean.entity.user.User;

import com.lss.education_resources_service.service.BaseService;
import com.lss.education_resources_service.service.FeedbackService;
import com.lss.education_resources_service.service.UploadService;
import com.lss.education_resources_service.util.respons.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/feedback")
@RestController
@Api(tags = "反馈相关接口")
public class FeedbackAPI extends BaseService {

    @Autowired
    private FeedbackService mFeedbackService;

    @Autowired
    private UploadService uploadService;

    @PostMapping
    @ApiOperation(value = "提交意见反馈（json方式)", notes = "只需要提交 content:String contact:String{mail,phone} type:[MACHINE_BUG,COMMON] 字段,images:图片链接,extra:当type为MACHINE_BUG的时候必填，值为机器编码")
    public API<Feedback> addFeedback(@RequestBody Feedback feedback) {
        User user = super.getCurrentUser();
        feedback.setUserId(user.getId());
        feedback.setUsername(user.getUsername());
        return API.ok(mFeedbackService.editFeedback(feedback));
    }


    @PostMapping({"/image"})
    @ApiOperation(value = "上传反馈图片")
    public API<String> updateEditorImage(@RequestPart MultipartFile image) {
        return API.ok(uploadService.getUploadFileDir(image));
    }
}
