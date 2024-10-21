package com.lyfx.domain.strategy.service.rule.tree.impl;

import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import com.lyfx.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.lyfx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:50
 * @description 库存规则节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Resource
    private IStrategyDispatch strategyDispatch;
    @Resource
    private IStrategyRepository repository;
    
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-库存扣减 START. userId: {} strategyId: {} awardId: {}", userId, strategyId, awardId);
        // 1. 扣减库存
        Boolean status = strategyDispatch.subtractAwardStock(strategyId, awardId);
        // true. 库存扣减成功
        if (status) {
            // 发送消息, 写入延迟队列, 延迟消费更新数据库记录
            // 【在trigger的job；UpdateAwardStockJob 下消费队列，更新数据库记录】
            repository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build());
            log.info("规则过滤-库存扣减 ALLOW. userId: {} strategyId: {} awardId: {} ruleValue: {}",
                    userId, strategyId, awardId, ruleValue);
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(awardId)
                            .awardRuleValue(ruleValue)
                            .build())
                    .build();
        }
        log.info("规则过滤-库存扣减 TAKEOVER. userId: {} strategyId: {} awardId: {} ruleValue: {}",
                userId, strategyId, awardId, ruleValue);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
