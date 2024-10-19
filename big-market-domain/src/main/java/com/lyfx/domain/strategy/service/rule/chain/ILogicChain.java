package com.lyfx.domain.strategy.service.rule.chain;

import com.lyfx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午3:18
 * @description 抽奖策略规则责任链接口
 */
public interface ILogicChain extends ILogicChainArmory, Cloneable{
    /**
     * 责任链接口
     * @param userId 用户ID
     * @param strategyId 抽奖策略ID
     * @return 奖品ID
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);
    
}
