package com.lyfx.domain.strategy.service;

import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/28 下午5:31
 * @description 抽奖规则接口
 */
public interface IRaffleRule {
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);
}
