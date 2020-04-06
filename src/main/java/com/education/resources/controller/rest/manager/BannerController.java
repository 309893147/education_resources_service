package com.education.resources.controller.rest.manager;

import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.entity.Banner;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.BannerService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/banner")
//@Api(tags = "Manager banner类")
public class BannerController {

    @Autowired
    BannerService bannerService;

//    @ApiOperation(value = "配置元信息")
    @GetMapping("/meta")
    public API<MetaData> bannerMeta() {
        return API.ok(MetaUtils.getMeta(Banner.class));
    }

    @GetMapping
//    @ApiOperation(value = "Banner列表")
    @PermissionDes(menu = {"Banner管理,Banner管理"}, rewriteMenu = true, name = "显示")
    public API<Page> bannerPage(Banner banner, PageForm pageForm) {
        return API.ok(bannerService.bannerPage(banner, pageForm));
    }

    @PostMapping
//    @ApiOperation(value = "增加Banner")
    @PermissionDes(menu = {"Banner管理,Banner管理"}, rewriteMenu = true, name = "保存")
    public API<Banner> addBanner(@RequestBody Banner banner) {
        return API.ok(bannerService.saveBanner(banner));
    }

    @DeleteMapping
//    @ApiOperation(value = "删除Banner")
    @PermissionDes(menu = {"Banner管理,Banner管理"}, rewriteMenu = true, name = "删除")
    public API deleteBanner(@RequestParam String ids) {
        bannerService.deleteBanner(ids);
        return API.ok();
    }
}
