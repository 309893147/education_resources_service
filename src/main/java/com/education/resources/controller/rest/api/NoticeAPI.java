package com.education.resources.controller.rest.api;

import com.education.resources.bean.entity.notice.Notice;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.notifaction.NoticeService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Api(tags = "小程序 公告通知接口")
@RequestMapping("/api/notice")
public class NoticeAPI {


    @Autowired
    private NoticeService noticeService;

//    @ApiOperation("获取公告通知")
    @GetMapping
    public API<Page<Notice>> getNotice(PageForm pageForm) {
        return API.ok(noticeService.getApiNoticeList(pageForm));
    }

//    @ApiOperation("获取公告通知详情")
    @GetMapping("/detail")
    public API<Notice> getNoticeDetail(Integer id) {
        return API.ok(noticeService.getOne(id));
    }



//    @ApiOperation("阅读公告")
//    @PostMapping("/read")
//    public API readNotice(Integer id) {
//        noticeService.readNotice(id);
//        return API.ok();
//    }


}
