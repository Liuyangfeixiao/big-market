package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.lyfx.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午9:45
 * @description 抽奖活动参与服务
 */
public interface IRaffleActivityPartakeService {
    /**
     * 创建抽奖单，用户参与抽奖活动，扣减活动账户库存、产生抽奖单。
     * 如果存在未被消费的抽奖单则返回已经存在的抽奖单
     * @param partakeRaffleActivityEntity 参与活动实体对象
     * @return 用户抽奖订单实体对象
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
    
    UserRaffleOrderEntity createOrder(String userId, Long activityId);
    
}
