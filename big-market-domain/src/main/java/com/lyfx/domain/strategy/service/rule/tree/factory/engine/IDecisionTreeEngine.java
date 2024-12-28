package com.lyfx.domain.strategy.service.rule.tree.factory.engine;

import com.lyfx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 下午2:53
 * @description 执行引擎，规则树的组合接口
 */
public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endDateTime);
}
