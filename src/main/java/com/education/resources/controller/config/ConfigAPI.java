package com.education.resources.controller.config;


import com.education.resources.annotation.PermissionDes;
import com.education.resources.bean.config.Config;
import com.education.resources.service.config.ConfigService;
import com.education.resources.util.rest.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage/config")
@Api(tags = "后台配置")
@ConditionalOnBean(ConfigService.class)
public class ConfigAPI {

    private ConfigService configService;

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }


    @GetMapping
    @ApiOperation(value = "查看配置")
    @PermissionDes(name = "查看系统设置",menu = {"设置","系统设置"})
    public API<List<Config>>  getAllConfig(){
        return API.ok(configService.getAllConfig());
    }


    @PostMapping
    @ApiOperation(value = "修改配置")
    @PermissionDes(name = "修改系统设置",menu = {"设置","系统设置"})
    public API<List<Config>> saveConfig(@RequestParam String configName, @RequestBody Map<String,String> values){
        return API.ok(configService.saveConfig(configName,values));
    }

}
