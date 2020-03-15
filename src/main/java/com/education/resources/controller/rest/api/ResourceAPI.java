package com.education.resources.controller.rest.api;

import com.education.resources.bean.entity.BasicType;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.BasicTypeService;
import com.education.resources.service.resource.ResouceService;
import com.education.resources.util.rest.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resource")
@Api(tags = "api 资源")
public class ResourceAPI {

    @Autowired
    ResouceService resouceService;

    @Autowired
    BasicTypeService basicTypeService;

    @GetMapping
    @ApiOperation(value = "教育资源列表")
    public API<Page> getResource(Resource resource, PageForm pageForm) {
        return API.ok(resouceService.getResource(resource, pageForm));
    }

    @GetMapping(value = "detail")
    @ApiOperation(value = "教育资源列表")
    public API<Resource> getOne(Integer id){
        return API.ok(resouceService.getOne(id));
    }



    @GetMapping(value = "type")
    @ApiOperation(value = "教育资源类型列表")
    public API<List<BasicType>> typePage(BasicType basicType, PageForm pageForm) {
        basicType.setType(BasicType.Type.RESOURCE);
        return API.ok(basicTypeService.typePage(basicType, pageForm));
    }

    @PostMapping
    @ApiOperation(value = "新增教育资源")
    public API<Resource> resourceAdd(@RequestBody Resource resource) {
        return API.ok(resouceService.wxResourceAdd(resource));
    }

    @DeleteMapping
    @ApiOperation(value = "删除教育资源")
    public API resourceDelete(@RequestParam String ids) {
        resouceService.resourceDelete(ids);
        return API.ok();
    }

}
