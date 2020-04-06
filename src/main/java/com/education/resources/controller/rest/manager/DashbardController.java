package com.education.resources.controller.rest.manager;

import com.education.resources.bean.entity.config.SystemConfig;
import com.education.resources.service.ManageDashboardService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
//@Api(tags = "数据统计")
@RequestMapping("/manage/common")
public class DashbardController {

    private ManageDashboardService dashboardService;


    @Autowired
    public void setDashboardService(ManageDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/index/count")
//    @ApiOperation(value = "首页面板")
    public API<Map> indexCount(){
        return  API.ok(dashboardService.getIndexData());
    }

    @GetMapping("/index/net")
//    @ApiOperation(value = "站点信息")
    public API<SystemConfig> serviceData(){
        return  API.ok(dashboardService.getServiceData());
    }

    @GetMapping("/index/service")
//    @ApiOperation(value = "服务器信息")
    public API<Map> getLoginData(){
        return  API.ok(dashboardService.getLoginData());
    }


}
