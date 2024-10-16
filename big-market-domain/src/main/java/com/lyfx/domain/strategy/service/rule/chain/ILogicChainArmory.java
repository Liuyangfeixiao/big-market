package com.lyfx.domain.strategy.service.rule.chain;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午9:18
 * @description 装配接口
 */
public interface ILogicChainArmory {
    // 添加新的节点
    ILogicChain appendNext(ILogicChain next);
    // 得到下一个节点
    ILogicChain next();
}
