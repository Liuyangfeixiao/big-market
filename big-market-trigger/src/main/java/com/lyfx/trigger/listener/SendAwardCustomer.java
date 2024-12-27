package com.lyfx.trigger.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message) {
        try {
            log.info("监听用户奖品发送消息 topic: {} message: {}", topic, message);
        } catch (Exception e) {
            log.error("监听奖品用户发送消息，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }
}
