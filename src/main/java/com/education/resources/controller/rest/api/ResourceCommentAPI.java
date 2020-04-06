package com.education.resources.controller.rest.api;

import com.education.resources.bean.entity.ResourceComment;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.resource.ResourceCommentService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
//@Api(tags = "api 资源评论")
public class ResourceCommentAPI {

    @Autowired
    ResourceCommentService resourceCommentService;


    @GetMapping
//    @ApiOperation(value = "评论列表")
    public API<Page> getResource(ResourceComment resourceComment, PageForm pageForm) {
        return API.ok(resourceCommentService.getComment(resourceComment, pageForm));
    }

    @PostMapping
//    @ApiOperation(value = "新增评论")
    public API<ResourceComment> resourceAdd(@RequestBody ResourceComment resourceComment) {
        return API.ok(resourceCommentService.save(resourceComment));
    }

    @DeleteMapping
//    @ApiOperation(value = "删除评论")
    public API resourceDelete(Integer id) {
        resourceCommentService.delete(id);
        return API.ok();
    }

}
