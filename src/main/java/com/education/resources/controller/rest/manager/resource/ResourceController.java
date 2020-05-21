package com.education.resources.controller.rest.manager.resource;


import com.alibaba.excel.exception.ExcelCommonException;
import com.education.resources.annotation.AnonUrl;
import com.education.resources.annotation.Meta;
import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.resource.ResouceService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/manage/resource")
//@Api(tags = "Manager 资源")
@PermissionDes(menu = {"资源管理","教育资源"})
public class ResourceController {

    @Autowired
    ResouceService resouceService;

//    @ApiOperation(value = "配置资源元信息")
    @GetMapping("/meta")
    public API<MetaData> getResourceMeta() {
        return API.ok(MetaUtils.getMeta(Resource.class));
    }


    @GetMapping
//    @ApiOperation(value = "教育资源列表")
    @PermissionDes( name = "显示")
    public API<Page> resourcePage(Resource resource, PageForm pageForm) {
        return API.ok(resouceService.resourcesPage(resource, pageForm));
    }

    @PostMapping
//    @ApiOperation(value = "新增教育资源")
    @PermissionDes(name = "保存")
    public API<Resource> resourceSave(@RequestBody Resource resource) {
        return API.ok(resouceService.resourceSave(resource));
    }

    @PostMapping(value = "review")
//    @ApiOperation(value = "审核教育资源")
    @PermissionDes(name = "审核资源")
    public API<Resource> resourceReview(@RequestBody Resource resource) {
        return API.ok(resouceService.resourceReview(resource));
    }
    @PostMapping(value = "import")
//    @ApiOperation(value = "新增教育资源")
    @PermissionDes(name = "导入")
    public API resourceData( MultipartFile excelFile) {

        try {
            return API.ok(resouceService.importResource(excelFile));
        } catch (IOException e) {
            return API.e(400, e.getMessage());
        } catch (ExcelCommonException e) {
            return API.e(400, "excel 文件解析失败");
        }
    }


    @DeleteMapping
//    @ApiOperation(value = "删除教育资源")
    @PermissionDes( name = "删除")
    public API resourceDelete(@RequestParam String ids) {
        resouceService.resourceDelete(ids);
        return API.ok();
    }



}
