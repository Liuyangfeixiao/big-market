package com.lyfx.domain.strategy.service.rule.impl;

import com.lyfx.domain.strategy.model.entity.RuleActionEntity;
import com.lyfx.domain.strategy.model.entity.RuleMatterEntity;
import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.annotation.LogicStrategy;
import com.lyfx.domain.strategy.service.rule.ILogicFilter;
import com.lyfx.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.lyfx.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午10:34
 * @description 积分权重过滤
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WEIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    @Resource
    private IStrategyRepository repository;
    public Long userScore = 4500L;
    
    /**
     * 积分权重规则过滤
     * 1. ruleValue格式: 4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式，判断哪个范围符合用户的特定抽奖范围
     * @param ruleMatterEntity 规则物料实体对象
     * @return 规则过滤结果
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重范围 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(),
                ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        
        String userId = ruleMatterEntity.getUserId();
        Long strategyId = ruleMatterEntity.getStrategyId();
        Integer awardId = ruleMatterEntity.getAwardId();
        String ruleValue = repository.queryStrategyRuleValue(strategyId, awardId, ruleMatterEntity.getRuleModel());
        
        // 1. 根据用户ID查询用户的积分值
        // TODO 现在先写死，之后再修改为从数据库查询
        Map<Long, String> parsedAwardGroup = parseRuleWeight(ruleValue);
        // 如果没有设置rule_weight, 放行
        if (null == parsedAwardGroup || parsedAwardGroup.isEmpty()) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }
        
        // 2. 转换key值并默认排序
        List<Long> parsedSortedKeys = new ArrayList<>(parsedAwardGroup.keySet());
        Collections.sort(parsedSortedKeys);
        Collections.reverse(parsedSortedKeys);
        
        // 3. 找出最小符合的值，也就是[4500 积分，能找到 4000:102,103,104,105], [5000积分，能找5000:102,103,...,107]
        Long nextValue = parsedSortedKeys.stream()
                .filter(key -> userScore >= key)
                .findFirst()
                .orElse(null);
        
        if (null != nextValue) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(strategyId)
                            .ruleWeightValueKey(parsedAwardGroup.get(nextValue))
                            .build())
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
    
    private Map<Long, String> parseRuleWeight(String ruleValue) {
        Map<Long, String> parsedAwardGroup = new HashMap<>();
        String[] awardGroups = ruleValue.split(Constants.SPACE);
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
}
