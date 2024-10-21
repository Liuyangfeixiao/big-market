package com.lyfx.domain.strategy.service.rule.tree;

import com.lyfx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:26
 * @description 规则树接口
 */
public interface ILogicTreeNode {
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue);
}
