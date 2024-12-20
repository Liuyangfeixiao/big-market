package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.model.entity.ActivityOrderEntity;
import com.lyfx.domain.activity.model.entity.ActivityShopCarEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午5:15
 * @description 抽奖活动订单接口
 */
public interface IRaffleOrder {
    /**
     * 以sku创建抽奖活动订单，获得参与抽奖资格（可消耗的次数）
     * @param activityShopCarEntity
     * @return 订单记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCarEntity activityShopCarEntity);
}
