package com.education.resources.service;



import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.auth.ManagerLoginLog;
import com.education.resources.bean.entity.Log;
import com.education.resources.bean.entity.config.SystemConfig;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.pojo.event.LogEvent;
import com.education.resources.datasource.repository.LogRepository;
import com.education.resources.datasource.repository.auth.ManagerLoginLogRepository;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogService extends BaseService {


    private LogRepository logRepository;

    @Autowired
    private ManagerLoginLogRepository managerLoginLogRepository;


    @Autowired
    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }



    public Page<Log> getLog(Log log, PageForm pageForm){
        PredicateBuilder<Log> spec = SpecificationUtil.filter(log, pageForm);
        return logRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public Page<ManagerLoginLog> getLoginLog(ManagerLoginLog managerLoginLog, PageForm pageForm){
        PredicateBuilder<ManagerLoginLog> spec = SpecificationUtil.filter(managerLoginLog, pageForm);
        return managerLoginLogRepository.findAll(spec.build(),pageForm.pageRequest());
    }



    @EventListener(SystemConfig.class)
    public void  onSystemConfigChange(SystemConfig systemConfig){
        saveConfigChangeLog(LogEvent.builder().content("编辑了系统配置").build());
    }


    private void saveConfigChangeLog(LogEvent  logEvent){
        Manager manager = getCurrentManager();
        logEvent.setName(manager.getName());
        logEvent.setIp(getIP());
        logEvent.setLevel(Log.Level.WARNING);
        logEvent.setType(Log.Type.OPERATION);
        logEvent.setOperation("编辑配置");
        onBusinessLog(logEvent);
    }

    @Async
    @EventListener(LogEvent.class)
    public void onBusinessLog(LogEvent logEvent){
        Log log = new Log();
        log.setContent(logEvent.getContent());
        log.setLevel(logEvent.getLevel());
        log.setType(logEvent.getType());
        log.setUserName(logEvent.getName());
        log.setManagerId(logEvent.getManagerId());
        log.setIp(logEvent.getIp());
        log.setOperation(logEvent.getOperation());
        logRepository.save(log);
    }
}
