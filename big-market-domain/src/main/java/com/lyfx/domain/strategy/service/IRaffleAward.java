package com.lyfx.domain.strategy.service;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/24 下午4:02
 * @description 策略奖品查询接口
 */
public interface IRaffleAward {
    /**
     * 根据策略ID查询抽奖奖品列表配置
     * @param strategyId
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
    /**
     * 根据策略ID查询抽奖奖品列表配置
     * @param activityId 活动ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardListByActivityId(Long activityId);
}
