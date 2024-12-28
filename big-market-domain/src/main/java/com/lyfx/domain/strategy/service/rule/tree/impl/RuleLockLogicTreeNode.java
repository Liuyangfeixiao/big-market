package com.lyfx.domain.strategy.service.rule.tree.impl;

import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.lyfx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:28
 * @description 次数锁规则节点
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    
    @Resource
    private IStrategyRepository strategyRepository;
    
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime) {
        log.info("规则过滤-次数锁 START. userId: {} strategyId: {} awardId: {}", userId, strategyId, awardId);
        
        long raffleCount = 0L;
        try {
            raffleCount = Long.parseLong(ruleValue);
        } catch (Exception e) {
            throw new RuntimeException("规则过滤-次数锁异常 ruleValue: " + ruleValue + " 配置不正确");
        }
        
        Integer userRaffleCount = strategyRepository.queryTodayUserRaffleCount(userId, strategyId);
        // 用户抽奖数大于规则限定值，放行
        if (userRaffleCount >= raffleCount) {
            log.info("规则过滤-次数锁 ALLOW. userId: {} strategyId: {} awardId: {} ruleValue: {}",
                    userId, strategyId, awardId, ruleValue);
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(awardId)
                            .awardRuleValue(ruleValue)
                            .build())
                    .build();
        }
        
        log.info("规则过滤-次数锁 TAKEOVER. userId: {} strategyId: {} awardId: {} ruleValue: {}",
                userId, strategyId, awardId, ruleValue);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
