//package com.education.resources.framework.rabbitmq.producer;
//
//
//import com.education.resources.datasource.repository.resource.ResourceRepository;
//import com.education.resources.framework.rabbitmq.mapper.BrokerMessageLogMapper;
//import com.renhe.datasource.mapper.OrganizationMapper;
//import com.renhe.datasource.repository.CommunityRepository;
//import com.renhe.task.constant.Constants;
//import com.renhe.vendor.rabbitmq.constants.RabbitConsts;
//import com.renhe.vendor.rabbitmq.mapper.BrokerMessageLogMapper;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RabbitSender {
//
//    //自动注入RabbitTemplate模板类
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private BrokerMessageLogMapper brokerMessageLogMapper;
//
//    @Autowired
//    private ResourceRepository resourceRepository;
//
//
//
//
//    //回调函数：confirm确认
//    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
//
//        @Override
//        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//            System.err.println("correlationData:" + correlationData);
//            String messageId = correlationData.getId();
//            if (ack) {
//                //如果confirm返回成功 则进行更新
//                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
//            } else {
//                //失败则进行具体的后续操作：重试或者补偿等手段
//                System.err.println("异常处理。。。");
//            }
//        }
//    };
//    //回调函数: return返回
//    final ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
//        @Override
//        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
//                                    String exchange, String routingKey) {
//            System.err.println("return exchange: " + exchange + ", routingKey: "
//                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
//        }
//    };
//
//    //发送消息方法调用: 构建Message消息
//    public void send(Object message, Map<String, Object> properties) throws Exception {
//        MessageHeaders mhs = new MessageHeaders(properties);
//        Message msg = (Message) MessageBuilder.createMessage(message, mhs);
//        rabbitTemplate.setConfirmCallback(confirmCallback);
//        rabbitTemplate.setReturnCallback(returnCallback);
//        //id + 时间戳 全局唯一
//        CorrelationData correlationData = new CorrelationData("1234567890" + System.nanoTime());
//        rabbitTemplate.convertAndSend("exchange-1", "springboot.#", msg, correlationData);
//    }
//
////    public void sendDelay(Order order) {
////
////        String orderName = order.getName();
////        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, orderName+new Date().getTime(),
////                message -> {
////                    Order order2 = orderMapper.findById(order.getId());
////                    String name = order2.getName();
////                    if (name == null){
////                        message.getMessageProperties().setHeader("x-delay", 2000);
////                        System.out.println("status生效");
////                        return message;
////                    }
////                    message.getMessageProperties().setHeader("x-delay", 2000);
////                    return message;
////                });
////		rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, orderName+new Date().getTime(),
////				message -> {
////                    Order order2 = orderMapper.findById(order.getId());
////                    String name = order2.getName();
////                    if (name == null){
////                        message.getMessageProperties().setHeader("x-delay", 2000);
////                        System.out.println("status生效");
////                        return message;
////                    }
////                    return  null;
////				});
////		rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, orderName+new Date().getTime(),
////				message -> {
////					message.getMessageProperties().setHeader("x-delay", 4000);
////					return message;
////				});
////    }
//
////    public void sendOwnerReflectDelay(OwnerReflect ownerReflect) {
////
////
////        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, ownerReflect,
////                message -> {
////                    message.getMessageProperties().setHeader("x-delay", 2000);
////                    return message;
////                });
////        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, ownerReflect,
////                message -> {
////                        message.getMessageProperties().setHeader("x-delay", 80000);
////                        System.out.println("status生效");
////                        return message;
////                });
////        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, ownerReflect,
////                message -> {
////                    message.getMessageProperties().setHeader("x-delay", 1000000);
////                    return message;
////                });
////    }
//
//    public void sendDelayMessage(Object msg,long time, TimeUnit unit){
//        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, msg,
//                message -> {
//                    message.getMessageProperties().setHeader("x-delay", unit.toMillis(time));
//                    return message;
//                });
//    }
//
////    //发送消息方法调用: 构建自定义对象消息
////    public void sendOrder(Order order) throws Exception {
////        rabbitTemplate.setConfirmCallback(confirmCallback);
////        rabbitTemplate.setReturnCallback(returnCallback);
////        //id + 时间戳 全局唯一
////        CorrelationData correlationData = new CorrelationData(order.getMessageId() + System.nanoTime());
////        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE, order, correlationData);
////    }
//
//}
