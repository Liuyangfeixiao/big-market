package com.lyfx.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.lyfx.domain.activity.model.entity.DeliveryOrderEntity;
import com.lyfx.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.lyfx.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.event.BaseEvent;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/7 20:24
 * @description
 */

@Slf4j
@Component
public class CreditAdjustSuccessCustomer {
    @Value("${spring.rabbitmq.topic.credit_adjust_success}")
    private String topic;
    
    @Resource
    private IRaffleActivityAccountQuotaService accountQuotaService;
    
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.credit_adjust_success}"))
    public void listener(String message) {
        try {
            log.info("监听积分账户调整成功消息，交易商品发货 START topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage>>() {
            }.getType());
            CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage creditAdjustSuccessMessage = eventMessage.getData();
            
            // 积分发货
            DeliveryOrderEntity deliveryOrderEntity = new DeliveryOrderEntity();
            deliveryOrderEntity.setUserId(creditAdjustSuccessMessage.getUserId());
            deliveryOrderEntity.setOutBusinessNo(creditAdjustSuccessMessage.getOutBusinessNo());
            accountQuotaService.updateOrder(deliveryOrderEntity);
            log.info("监听积分账户调整成功消息，交易商品发货 FINISHED topic: {} message: {}", topic, message);
        } catch (AppException e) {
          if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
              log.warn("监听积分账户调整成功消息，进行交易商品发货，消费重复 topic: {} message: {}", topic, message, e);
              return;
          }
          throw e;
        } catch (Exception e) {
            log.error("监听积分账户调整成功消息，进行交易商品发货 FAILED. topic: {} message: {}", topic, message, e);
            throw e;
        }
    }
}
