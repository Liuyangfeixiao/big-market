package com.lyfx.domain.strategy.service;

import com.lyfx.domain.strategy.model.vo.StrategyAwardStockKeyVO;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/21 下午4:56
 * @description 抽奖库存相关服务，获取库存消耗队列
 */
public interface IRaffleStock {
    /**
     * 获取奖品库存消耗队列
     * @return 奖品库存Key信息
     * @throws InterruptedException
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;
    
    /**
     * 更新奖品库存消耗记录
     * @param strategyId 策略Id
     * @param awardId 奖品Id
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);
}
