package com.education.resources.controller.rest.manager.notice;

import com.education.resources.annotation.Meta;
import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.entity.notice.Notice;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.notifaction.NoticeService;
import com.education.resources.service.resource.ResouceService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/notice")
//@Api(tags = "Manager 公告")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

//    @ApiOperation(value = "配置元信息")
    @GetMapping("/meta")
    public API<MetaData> userMeta() {
        return API.ok(MetaUtils.getMeta(Notice.class));
    }


    @GetMapping
//    @ApiOperation(value = "通知公告列表")
    @PermissionDes(menu = {"通知公告"}, name = "显示")
    public API<Page> complaintList(Notice notice, PageForm pageForm) {
        return API.ok(noticeService.list(notice, pageForm));
    }

    @PostMapping
//    @ApiOperation(value = "保存通知公告")
    public API<Notice> noticeSave(@RequestBody Notice notice) {
        return API.ok(noticeService.noticeSave(notice));
    }

    @DeleteMapping
//    @ApiOperation(value = "删除通知公告")
    public API noticeDelete(@RequestParam String ids) {
        noticeService.noticeDelete(ids);
        return API.ok();
    }



}
