package com.lyfx.domain.activity.service.rule;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午5:26
 * @description 下单规则责任链抽象类
 */
public abstract class AbstractActionChain implements IActionChain {
    private IActionChain next;
    
    @Override
    public IActionChain next() {
        return next;
    }
    
    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
