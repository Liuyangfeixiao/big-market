package com.lyfx.domain.strategy.service.rule.chain.factory;

import com.lyfx.domain.strategy.model.entity.StrategyEntity;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.rule.chain.ILogicChain;
import com.lyfx.domain.strategy.service.rule.filter.ILogicFilter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午8:03
 * @description 抽奖责任链工厂, 产生责任链
 */
@Service
public class DefaultChainFactory {
    private final Map<String, ILogicChain> logicChainMap;
    private final IStrategyRepository repository;
    
    public DefaultChainFactory(Map<String, ILogicChain> logicChainMap, IStrategyRepository repository) {
        this.logicChainMap = logicChainMap;
        this.repository = repository;
    }
    
    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();
        if (ruleModels == null || ruleModels.length == 0) {
            return logicChainMap.get("default");
        }
        // 首先获取第0个chain
        ILogicChain logicChain = logicChainMap.get(ruleModels[0]);
        // 之后遍历之后的rule chain
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = logicChainMap.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }
        // 最后添加一个 default chain
        current.appendNext(logicChainMap.get("default"));
        
        return logicChain;
    }
}
