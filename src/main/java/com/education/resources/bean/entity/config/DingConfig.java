package com.education.resources.bean.entity.config;

import com.education.resources.annotation.ConfigBean;
import com.education.resources.annotation.ConfigDes;
import com.education.resources.bean.config.ConfigProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ConfigBean(name = "钉钉设置")
public class DingConfig {
    @ConfigDes(title = "自定义机器人关键字",defaultValue = "",require = true,description = "资源")
    private String keyWord;

    @ConfigDes(title = "自定义机器人群地址",defaultValue = "",require = true,description = "")
    private String webhook;
}
