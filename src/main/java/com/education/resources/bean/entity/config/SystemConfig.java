package com.education.resources.bean.entity.config;


import com.education.resources.annotation.ConfigBean;
import com.education.resources.annotation.ConfigDes;
import com.education.resources.bean.config.ConfigProperty;
import lombok.Data;

@Data
@ConfigBean(name = "系统设置")
public class SystemConfig {

    @ConfigDes(title = "域名",defaultValue = "refactor.renhe.demo.cq1080.com",require = true,description = "如cq1080.com")
    private ConfigProperty  domain;

    @ConfigDes(title = "工作提醒时间",defaultValue = "9:30",require = true,description = "每天工作提醒待处理业务")
    private ConfigProperty remindTime;

}
