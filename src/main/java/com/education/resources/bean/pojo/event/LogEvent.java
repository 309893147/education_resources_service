package com.education.resources.bean.pojo.event;

import com.education.resources.bean.entity.Log;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogEvent {

    private String content;

    private Log.Level  level;

    private String ip;

    private String name;

    private Integer  managerId;

    //操作
    private String operation;

    Log.Type type;
}
