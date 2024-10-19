package com.lyfx.domain.strategy.service.rule.chain.impl;

import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import com.lyfx.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.lyfx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.lyfx.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午4:01
 * @description 权重责任链
 */

@Slf4j
@Component("rule_weight")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RuleWeightLogicChain extends AbstractLogicChain {
    
    @Resource
    private IStrategyRepository repository;
    @Resource
    protected IStrategyDispatch strategyDispatch;
    // 根据用户ID查询用户抽奖消耗的积分值，本章节我们先写死为固定的值。后续需要从数据库中查询。
    public Long userScore = 0L;
    
    /**
     * 积分权重规则过滤
     * 1. ruleValue格式: 4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式，判断哪个范围符合用户的特定抽奖范围
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-rule-weight START. userId={} strategyId={} ruleModel: {}", userId, strategyId, ruleModel());
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        
        // 1. 根据用户ID查询用户的积分值
        // TODO 现在先写死，之后再修改为从数据库查询
        Map<Long, String> analyticalValueGroup = getAnalyticalValueGroup(ruleValue);
        // 没有配置 rule_weight 权重
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) {
            log.warn("抽奖责任链-rule-weight 告警【策略配置积分权重，但ruleValue未配置相应值】. userId: {} strategyId: {} ruleModel: {}",
                    userId, strategyId, ruleModel());
            return next().logic(userId, strategyId);
        }
        // 转换key值并逆序排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);
        Collections.reverse(analyticalSortedKeys);
        
        // 3. 找出最小符合的值，也就是[4500 积分，能找到 4000:102,103,104,105], [5000积分，能找5000:102,103,...,107]
        Long nextValue = analyticalSortedKeys.stream()
                .filter(key -> userScore >= key)
                .findFirst()
                .orElse(null);
        
        // 4. 通过积分权重进行抽奖
        if (null != nextValue) {
            // 4000:102,103,104,105 -> 4000
            String weightValue = analyticalValueGroup.get(nextValue).split(Constants.COLON)[0];
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, weightValue);
            log.info("抽奖责任链-rule-weight TAKEOVER. userId: {} strategyId: {} ruleModel: {} awardId: {}",
                    userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }
        log.info("抽奖责任链-rule-weight ALLOW. userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }
    
    private Map<Long, String> getAnalyticalValueGroup(String ruleValue) {
        String[] awardGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> parsedAwardGroup = new HashMap<>();
        for (String ruleValueKey : awardGroups) {
            // 检查输入是否为空
            if (null == ruleValueKey || ruleValueKey.isEmpty()) {
                return parsedAwardGroup;
            }
            String[] parts = ruleValueKey.split(Constants.COLON);
            // 判断格式是否正确
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight invalid input format: " + ruleValueKey);
            }
            parsedAwardGroup.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return parsedAwardGroup;
    }
    
    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }
    
    
}
