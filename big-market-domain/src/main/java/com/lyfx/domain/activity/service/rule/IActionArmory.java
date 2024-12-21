package com.lyfx.domain.activity.service.rule;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午4:54
 * @description 下单规则装配接口
 */
public interface IActionArmory {
    
    IActionChain next();
    
    IActionChain appendNext(IActionChain next);
}
