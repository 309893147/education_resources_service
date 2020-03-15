package com.education.resources.event;

import lombok.Data;

@Data
public class ExpireEvent {
    private Long createTime;
    private Long  expireTime;
    private Source  source;
    private Integer  sourceId;

    public ExpireEvent(Long createTime, Long expireTime, Source source, Integer sourceId) {
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.source = source;
        this.sourceId = sourceId;
    }

    public enum Source{
        AFFAIR,//事务
    }
}
