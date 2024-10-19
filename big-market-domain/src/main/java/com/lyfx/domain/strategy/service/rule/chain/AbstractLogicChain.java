package com.lyfx.domain.strategy.service.rule.chain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午3:25
 * @description
 */

@Slf4j
public abstract class AbstractLogicChain implements ILogicChain{
    private ILogicChain next;
    
    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }
    
    @Override
    public ILogicChain next() {
        return next;
    }
    
    protected abstract String ruleModel();  // 让每个LogicChain自己提供对象名
}
