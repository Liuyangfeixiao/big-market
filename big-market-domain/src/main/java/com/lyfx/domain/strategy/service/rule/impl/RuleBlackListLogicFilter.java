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
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午8:38
 * @description
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class RuleBlackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    @Resource
    private IStrategyRepository repository;
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-黑名单 userId: {}, strategyId: {}, ruleModel: {}", ruleMatterEntity.getUserId(),
                ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId = ruleMatterEntity.getUserId();
        
        // 查询规则配置的值 100:user001,user002,user003
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(),
                                                                ruleMatterEntity.getRuleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);
        
        // 开启过滤，查看userId是否在黑名单中
        Set<String> userBlacklist = Arrays.stream(splitRuleValue[1].split(Constants.SPLIT))
                                    .collect(Collectors.toSet());
        boolean isInBlackList = userBlacklist.contains(userId);
        if (isInBlackList) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .ruleModel(ruleMatterEntity.getRuleModel())
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(ruleMatterEntity.getStrategyId())
                            .awardId(awardId)
                            .build())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
