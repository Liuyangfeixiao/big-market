package com.lyfx.domain.strategy.service.rule.tree.impl;

import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.lyfx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.lyfx.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:46
 * @description 幸运兜底规则节点
 */

@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime) {
        // ruleValue 的形式为 101:1,100
        log.info("规则过滤-兜底奖品 START. userId: {} strategyId: {} awardId: {} ruleValue: {}",
                userId, strategyId, awardId, ruleValue);
        String[] split = ruleValue.split(Constants.COLON);
        if (split.length == 0) {
            log.error("规则过滤-兜底奖品 WARN. 兜底奖品未配置报警 userId: {} strategyId: {} awardId: {}",
                    userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置: " + ruleValue);
        }
        // 兜底奖励配置
        Integer luckAwardId =Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";
        
        // 返回兜底奖品
        log.info("规则过滤-兜底奖品 TAKEOVER. userId: {} strategyId: {} awardId: {} ruleValue: {}",
                userId, strategyId, luckAwardId, awardRuleValue);
        
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();
    }
}
