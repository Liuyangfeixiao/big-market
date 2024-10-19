package com.lyfx.domain.strategy.service.rule.chain.impl;

import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import com.lyfx.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.lyfx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午4:03
 * @description 兜底责任链节点[默认作为最后一个责任链节点]
 */

@Slf4j
@Component("default")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultLogicChain extends AbstractLogicChain {
    
    @Resource
    protected IStrategyDispatch strategyDispatch;
    
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        // 日志打印
        log.info("责任链-default TAKEOVER. userId: {} strategyId: {} ruleModel: {} awardId: {}",
                userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }
    
    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }
}
