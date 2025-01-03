package com.lyfx.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.lyfx.domain.activity.model.entity.SkuRechargeEntity;
import com.lyfx.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.lyfx.domain.rebate.event.SendRebateMessageEvent;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.event.BaseEvent;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 23:00
 * @description 监听：行为返利消息
 */


@Slf4j
@Component
public class RebateMessageCustomer {
    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;
    
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void  listener(String message) {
        try {
            log.info("监听用户行为返利消息: topic: {} message: {}", topic, message);
            // 1. 转换消息
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {
            }.getType());
            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();
            
            // 2. 入账奖励
            if (rebateMessage.getRebateType().equals("sku")) {
                SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                skuRechargeEntity.setUserId(rebateMessage.getUserId());
                skuRechargeEntity.setSku(Long.valueOf(rebateMessage.getRebateConfig()));
                skuRechargeEntity.setOutBusinessNo(rebateMessage.getBizId());
                raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
            } else {
                log.info("监听用户行为返利消息 - 非SKU奖励暂时不处理 topic: {} message: {}", topic, message);
            }
            
        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听用户行为返利消息，消费重复 topic: {} message: {}", topic, message, e);
                return;
            }
            throw e;
        } catch (Exception e) {
            log.error("监听用户行为返利消息，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }
}
