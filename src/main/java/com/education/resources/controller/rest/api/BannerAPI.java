package com.education.resources.controller.rest.api;

import com.education.resources.bean.entity.Banner;
import com.education.resources.service.BannerService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/banner")
@RestController
//@Api(tags = "API banner")
public class BannerAPI {

    @Autowired
    BannerService bannerService;

    @GetMapping
    public API<List<Banner>> getBanner() {
        return API.ok(bannerService.bannerList());
    }

}
