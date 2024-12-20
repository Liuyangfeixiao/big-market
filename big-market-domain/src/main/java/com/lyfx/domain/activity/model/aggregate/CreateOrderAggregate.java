package com.lyfx.domain.activity.model.aggregate;

import com.lyfx.domain.activity.model.entity.ActivityAccountEntity;
import com.lyfx.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午5:38
 * @description 下单聚合对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {
    /**
     * 活动账户实体
     */
    private ActivityAccountEntity activityAccountEntity;
    /**
     * 活动订单实体
     */
    private ActivityOrderEntity activityOrderEntity;
    
}
