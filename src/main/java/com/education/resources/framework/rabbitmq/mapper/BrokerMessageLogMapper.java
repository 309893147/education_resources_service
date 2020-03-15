package com.education.resources.framework.rabbitmq.mapper;

import com.education.resources.framework.rabbitmq.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface BrokerMessageLogMapper {

    @Insert("insert into broker_message_log (message_id, message," +
            "status, next_retry, create_time," +
            "update_time)" +
            "values (#{messageId}, #{message}," +
            "#{status}, #{nextRetry}, #{createTime}," +
            "#{updateTime})")
    int insert(BrokerMessageLog record);

    @Select(" select message_id, message, try_count, status, next_retry, create_time, update_time from broker_message_log bml")
    List<BrokerMessageLog> queryMessage();

    /**
     * 查询消息状态为0(发送中) 且已经超时的消息集合
     * @return
     */
    @Select(" select message_id, message, try_count, status, next_retry, create_time, update_time from broker_message_log bml where status = '0' and next_retry <= sysdate()")
    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    /**
     * 重新发送统计count发送次数 +1
     * @param messageId
     * @param updateTime
     */
    @Update("update broker_message_log bml set bml.try_count = bml.try_count + 1 , bml.update_time = #{updateTime} where bml.message_id = #{messageId}")
    void update4ReSend(@Param("messageId") String messageId, @Param("updateTime") Date updateTime);
    /**
     * 更新最终消息发送结果 成功 or 失败
     * @param messageId
     * @param status
     * @param updateTime
     */
    @Update("update broker_message_log bml set bml.status = #{status},bml.update_time = #{updateTime} where bml.message_id = #{messageId}")
    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);



}
