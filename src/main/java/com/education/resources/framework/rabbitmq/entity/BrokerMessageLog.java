package com.education.resources.framework.rabbitmq.entity;



import com.education.resources.bean.entity.BaseEntity;
import com.education.resources.util.TimestampDeserializer;
import com.education.resources.util.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class BrokerMessageLog extends BaseEntity {

    @ApiModelProperty(value = "消息Id")
    private String messageId;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "尝试次数")
    private Integer tryCount;

    @ApiModelProperty(value = "状态")
    private String status;

    @UpdateTimestamp
    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @ApiModelProperty(value = "下次重试时间")
    protected Date nextRetry;

}
