package com.lyfx.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.lyfx.domain.award.event.SendAwardMessageEvent;
import com.lyfx.domain.award.model.entity.DistributeAwardEntity;
import com.lyfx.domain.award.service.IAwardService;
import com.lyfx.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/27 下午3:09
 * @description 用户奖品记录信息消费者
 */

@Slf4j
@Component
public class SendAwardCustomer {
    @Value("${spring.rabbitmq.topic.send_award}")
    private String topic;
    
    @Resource
    private IAwardService awardService;
    
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message) {
        try {
            log.info("监听用户奖品发送消息 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>>() {
            }.getType());
            
            SendAwardMessageEvent.SendAwardMessage sendAwardMessage = eventMessage.getData();
            // 发放奖品
            DistributeAwardEntity distributeAwardEntity = new DistributeAwardEntity();
            distributeAwardEntity.setUserId(sendAwardMessage.getUserId());
            distributeAwardEntity.setOrderId(sendAwardMessage.getOrderId());
            distributeAwardEntity.setAwardId(sendAwardMessage.getAwardId());
            distributeAwardEntity.setAwardConfig(sendAwardMessage.getAwardConfig());
            awardService.distributeAward(distributeAwardEntity);
            
            log.info("监听用户奖品发送消息，发奖完成. topic: {} message: {}", topic, message);
            
        } catch (Exception e) {
            log.error("监听奖品用户发送消息，发奖失败 topic: {} message: {}", topic, message, e);
//            throw e;
        }
    }
}
