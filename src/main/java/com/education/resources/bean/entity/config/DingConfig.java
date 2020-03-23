package com.education.resources.bean.entity.config;

import com.education.resources.annotation.ConfigBean;
import com.education.resources.annotation.ConfigDes;
import com.education.resources.bean.config.ConfigProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ConfigBean(name = "钉钉设置")
public class DingConfig {
    @ConfigDes(title = "自定义机器人关键字",defaultValue = "小优机器人",require = true,description = "机器人发送消息关键字")
    private ConfigProperty keyWord;

    @ConfigDes(title = "自定义机器人群地址",defaultValue = "https://oapi.dingtalk.com/robot/send?access_token=6bbb623676ed3b78a843f2a2554d12bfe61b3c95fb594ee2b73b972193a3615c",require = true,description = "钉钉自定义机器人地址")
    private ConfigProperty webHook;
}
