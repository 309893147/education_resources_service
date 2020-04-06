//package com.education.resources.controller.rest.api;
//
//
//import com.education.resources.bean.entity.feedback.Feedback;
//import com.education.resources.service.FeedbackService;
//import com.education.resources.util.rest.API;
////import io.swagger.annotations.Api;
////import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/api/feedback")
//@RestController
////@Api(tags = "反馈相关接口")
//public class FeedbackAPI {
//
//    @Autowired
//    private FeedbackService mFeedbackService;
//
//
//    @PostMapping
////    @ApiOperation(value = "提交意见反馈（json方式)", notes = "只需要提交 content:String contact:String{mail,phone} type:[MACHINE_BUG,COMMON] 字段,images:图片链接,extra:当type为MACHINE_BUG的时候必填，值为机器编码")
//    public API<Feedback> addFeedback(@RequestBody Feedback feedback) {
//        return API.ok(mFeedbackService.editFeedback(feedback));
//    }
//
//}
