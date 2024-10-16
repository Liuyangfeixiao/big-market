package com.lyfx.domain.strategy.service.rule.chain.impl;

import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.lyfx.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午4:00
 * @description 黑名单方法
 */

@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {
    @Resource
    private IStrategyRepository repository;  // 需要从数据库中查询
    
    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链-blacklist START. userId: {} strategyId: {} ruleModel: {}",
                userId, strategyId, ruleModel());
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        // 100:user001,user002,user003
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.valueOf(splitRuleValue[0]);
        // 如果用户在黑名单中
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-blacklist TAKEOVER. userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return awardId;
            }
        }
        
        // 不在黑名单中，过滤其他责任链
        log.info("抽奖责任链-blacklist ALLOW. userId{} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }
    
    @Override
    protected String ruleModel() {
        return "rule_blacklist";
    }
}
