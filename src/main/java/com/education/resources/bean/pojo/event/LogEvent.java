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

    private Integer communityId;

    private String userName;

    private Integer  employeeId;

    private Integer  userId;

    //操作
    private String operation;

    Log.Type type;
}
