package com.education.resources.bean.pojo.event;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DingMessageEvent implements Serializable {
    private String title;
    private String content;
    private String mention;
    public String getContent() {
        return content == null? "有新的资源提交，请及时受理":content;
    }
}
