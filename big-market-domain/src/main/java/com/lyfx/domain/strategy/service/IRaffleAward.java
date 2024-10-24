package com.lyfx.domain.strategy.service;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/24 下午4:02
 * @description 策略奖品查询接口
 */
public interface IRaffleAward {
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
}
