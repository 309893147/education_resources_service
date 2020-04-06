package com.education.resources.controller.rest.manager;


import com.education.resources.bean.auth.ManagerLoginLog;
import com.education.resources.bean.entity.Log;
import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.service.LogService;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Api(tags = "系统管理")
@RequestMapping("/manage/log")
public class LogController {
    private LogService logService;

    @Autowired
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

//    @ApiOperation("操作日志管理")
    @GetMapping("operation")
    public API<Page<Log>> getOperationLog(Log log, PageForm pageForm) {
        log.setType(Log.Type.OPERATION);
        return API.ok(logService.getLog(log, pageForm));
    }

    @GetMapping("system")
//    @ApiOperation("系统日志管理")
    public API<Page<Log>> getSystemLog(Log log, PageForm pageForm) {
        log.setType(Log.Type.SYSTEM);
        return API.ok(logService.getLog(log, pageForm));
    }

    @GetMapping("login")
//    @ApiOperation("登陆日志管理")
    public API<Page<ManagerLoginLog>> getSystemLog(ManagerLoginLog managerLoginLog, PageForm pageForm) {

        return API.ok(logService.getLoginLog(managerLoginLog, pageForm));
    }

    @GetMapping("/meta")
    public API<MetaData> getMeta() {
        return API.ok(MetaUtils.getMeta(Log.class));
    }

    @GetMapping("/login/meta")
    public API<MetaData> getLoginMeta() {
        return API.ok(MetaUtils.getMeta(ManagerLoginLog.class));
    }


}
