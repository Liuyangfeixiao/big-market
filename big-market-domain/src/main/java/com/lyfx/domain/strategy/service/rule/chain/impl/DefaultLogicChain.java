package com.lyfx.domain.strategy.service.rule.chain.impl;

import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import com.lyfx.domain.strategy.service.rule.chain.AbstractLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午4:03
 * @description 兜底责任链节点
 */

@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {
    
    @Resource
    protected IStrategyDispatch strategyDispatch;
    
    @Override
    public Integer logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        // 日志打印
        log.info("责任链-default TAKEOVER. userId: {} strategyId: {} ruleModel: {} awardId: {}",
                userId, strategyId, ruleModel(), awardId);
        return awardId;
    }
    
    @Override
    protected String ruleModel() {
        return "default";
    }
}
