package com.education.resources.controller.rest.api;

import com.education.resources.bean.entity.ResourceUserDownload;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.resource.ResourceUserDownloadService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource/download")
//@Api(tags = "api 资源评论")
public class ResourceUserDownloadAPI {

    @Autowired
    ResourceUserDownloadService resourceUserDownloadService;

    @PostMapping
//    @ApiOperation(value = "教育资源下载成功反馈")
    public API downloadSuccess(@RequestBody  ResourceUserDownload resourceUserDownload){
        resourceUserDownloadService.downloadSuccess(resourceUserDownload);
        return API.ok();
    }

    @GetMapping(value = "/can")
//    @ApiOperation(value = "校验用户是否还有积分下载资源")
    public API<Integer> canDownloadResource() {
        return API.ok(resourceUserDownloadService.canDownloadResource());
    }

    @GetMapping(value = "my/download")
//    @ApiOperation(value = "获取我下载的教育资源")
    public API<Page<ResourceUserDownload>> getMyDownloadResource(PageForm pageForm) {
        return API.ok(resourceUserDownloadService.getMyDownloadResource(pageForm));
    }

    @PostMapping(value = "my/download/score")
//    @ApiOperation(value = "我下载的教育资源打分")
    public API<ResourceUserDownload> commentScore(@RequestBody ResourceUserDownload resourceScoreFrom) {
        return API.ok(resourceUserDownloadService.commentScore(resourceScoreFrom));
    }
}
