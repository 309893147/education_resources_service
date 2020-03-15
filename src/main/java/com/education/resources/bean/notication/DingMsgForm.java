package com.education.resources.bean.notication;

import com.education.resources.bean.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DingMsgForm extends BaseEntity {

    @ApiModelProperty(value = "自定义机器人关键字")
    private String keyWord;

    @ApiModelProperty(value = "首屏会话透出的展示内容")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "link 消息内容。如果太长只会部分展示 markdown markdown格式的消息")
    private String text;

    @ApiModelProperty(value = "钉钉消息类型")
    private DingType dingType;

    @ApiModelProperty(value = "link 点击消息跳转的URL")
    private String messageUrl;

    @ApiModelProperty(value = "link 图片URL")
    private String picUrl;

    @ApiModelProperty(value = "text @的用户")
    private String mobiles;

    @ApiModelProperty(value = "自定义机器人 群地址")
    private String webhook;

    public enum DingType{
        TEXT,
        LINK,
        MARKDOWN,
        ACTIONCARD,
        FEEDCARD,
    }

    @ApiModelProperty(value = "自定义机器人 link 跳转链接")
    private List<Link> links;

    @Data
    @AllArgsConstructor
    public static class Link{
        @ApiModelProperty(value = "标题")
        private String title;
        @ApiModelProperty(value = "link 点击消息跳转的URL")
        private String messageUrl;
        @ApiModelProperty(value = "link 图片")
        private String picUrl;

        public Link() {
        }
    }



}
