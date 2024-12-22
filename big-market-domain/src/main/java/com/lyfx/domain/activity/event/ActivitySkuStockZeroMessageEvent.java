package com.lyfx.domain.activity.event;

import com.lyfx.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/22 下午7:20
 * @description 活动sku库存清空信息
 */

@Component
public class ActivitySkuStockZeroMessageEvent extends BaseEvent<Long> {
    
    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;
    
    @Override
    public EventMessage<Long> buildEventMessage(Long sku) {
        return EventMessage.<Long>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(sku)
                .build();
    }
    
    @Override
    public String topic() {
        return topic;
    }
}
