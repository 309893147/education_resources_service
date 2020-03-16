package com.education.resources.controller.rest.manager;

import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.entity.BasicType;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.BasicTypeService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/type")
@Api(tags = "基础类型设置")
@PermissionDes
public class BasicTypeController {

    @Autowired
    BasicTypeService basicTypeService;

    public API<Page> getList(BasicType basicType, PageForm pageForm, BasicType.Type type){
        basicType.setType(type);
        return API.ok(basicTypeService.list(basicType, pageForm));
    }

    @GetMapping(value = "/detail")
    @ApiOperation(value = "根据id查询")
    public API<BasicType> getOne(Integer id){
        return API.ok(basicTypeService.getOne(id));
    }

//    @GetMapping(value = "/name")
//    @ApiOperation(value = "基础类型名称")
//    public API<List<BasicType>> enumTypeList(String type) {
//        List<BasicType> list = basicTypeService.enumTypeList(type);
//        return API.ok(list);
//    }


    @ApiOperation(value = "配置元信息")
    @GetMapping("/resource/meta")
    public API<MetaData> userMeta() {
        return API.ok(MetaUtils.getMeta(BasicType.class));
    }

    @GetMapping(value = "/resource")
    @ApiOperation(value = "资源类型列表")
    @PermissionDes(menu = {"资源类型管理"}, name = "显示")
    public API<Page> complaintList(BasicType basicType, PageForm pageForm) {
        return getList(basicType,pageForm, BasicType.Type.RESOURCE);
    }

    @PostMapping(value = "/resource")
    @ApiOperation(value = "保存资源类型")
    @PermissionDes(menu = {"资源类型管理"}, name = "增加")
    public API<BasicType> addComplaint(@RequestBody BasicType basicType) {
        return API.ok(basicTypeService.save(basicType));
    }

    @DeleteMapping(value = "/resource")
    @ApiOperation(value = "删除资源类型")
    @PermissionDes(menu = {"资源类型管理"}, name = "删除")
    public API deleteComplaint(@RequestParam String ids) {
        basicTypeService.delete(ids);
        return API.ok();
    }

}
