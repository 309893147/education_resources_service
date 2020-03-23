//package com.education.resources.framework.rabbitmq.conusmer;//package com.bfxy.springboot.conusmer;
//
//import com.education.resources.bean.entity.Resource;
//import com.education.resources.bean.entity.config.DingConfig;
//import com.education.resources.bean.notication.DingMsgForm;
//import com.education.resources.datasource.repository.resource.ResourceRepository;
//import com.education.resources.framework.rabbitmq.constants.RabbitConsts;
//import com.education.resources.service.BaseService;
//import com.education.resources.service.notifaction.DingMsgService;
//import com.google.gson.Gson;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RabbitListener(queues = RabbitConsts.DELAY_QUEUE)
//public class RabbitReceiver extends BaseService {
//	private Gson gson = new Gson();
//
//	DingMsgService dingMsgService = new DingMsgService();
//
//	@Autowired
//	ResourceRepository resourceRepository;
//
//	DingConfig dingConfig;
//
//	@RabbitHandler
//	public void directHandlerManualAck(Resource resource, Message message, Channel channel) {
//		//  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
//		final long deliveryTag = message.getMessageProperties().getDeliveryTag();
//		try {
//			Resource resource1 = resourceRepository.findItemById(resource.getId());
//			if (resource1.getResourceStatus() == Resource.ResourceStatus.UNPROCESSED){
////				Community community = communityRepository.findItemById(ownerReflect1.getCommunityId());
//
//				dingConfig=getConfig(DingConfig.class);
//				System.out.println(dingConfig.getWebHook());
//
//				DingMsgForm dingMsgForm = new DingMsgForm();
//				dingMsgForm.setWebhook(dingConfig.getWebHook());
////				dingMsgForm.setMobiles(dingConfig.getDingPhone());
//				dingMsgForm.setTitle("资源上传: 赶快处理"+resource1.getTitle());
//				dingMsgForm.setText(resource1.getContent());
//				dingMsgForm.setDingType(DingMsgForm.DingType.MARKDOWN);
//				dingMsgService.sendMsg(dingMsgForm);
//				log.info("延迟队列，手动ACK，成功发送给钉钉：{}", gson.toJson(resource1.toString()));
//			}
//			log.info("延迟队列，手动ACK，接收消息：{}", gson.toJson(resource1.toString()));
//			// 通知 MQ 消息已被成功消费,可以ACK了
//			channel.basicAck(deliveryTag, false);
//		} catch (IOException e) {
//			try {
//				// 处理失败,重新压入MQ
//				channel.basicRecover();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//
//
//}
