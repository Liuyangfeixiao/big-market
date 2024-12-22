package com.lyfx.trigger.listener;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.lyfx.domain.activity.service.ISkuStock;
import com.lyfx.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/22 下午8:51
 * @description 活动sku库存耗尽
 */

@Slf4j
@Component
public class ActivitySkuStockZeroCustomer {
    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;
    @Resource
    ISkuStock skuStock;
    
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.activity_sku_stock_zero}"))
    public void listener(String message) {
        try {
            log.info("监听活动sku库存消耗为0消息 topic: {} message: {}", topic, message);
            // 转换对象
            BaseEvent.EventMessage<Long> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<Long>>() {
            }.getType());
            Long sku = eventMessage.getData();
            // 更新库存--数据库
            skuStock.clearActivitySkuStock(sku);
            // 清空队列--redis
            skuStock.clearQueueValue(sku);
        } catch (Exception e) {
            log.error("监听活动sku库存消耗为0消息，消费失败 topic: {} message: {}", topic, message);
            throw e;
        }
        
    }
}