package com.education.resources.framework.rabbitmq.mapper;//package com.renhe.vendor.rabbitmq.mapper;
//
//import com.renhe.vendor.rabbitmq.entity.BrokerMessageLog;
//import com.renhe.vendor.rabbitmq.entity.Order;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.stereotype.Component;
//
//@Component
//@Mapper
//public interface OrderMapper {
//
//    @Insert("insert into t_order (id,name,message_id) values(#{id},#{name},#{messageId})")
//    int insert(Order order);
//
//    @Select("select * from t_order where id = #{id}")
//    Order findById(String id);
//}
