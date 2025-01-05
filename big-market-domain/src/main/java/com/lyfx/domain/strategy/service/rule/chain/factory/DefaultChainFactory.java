package com.lyfx.domain.strategy.service.rule.chain.factory;

import com.lyfx.domain.strategy.model.entity.StrategyEntity;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/16 下午8:03
 * @description 抽奖责任链工厂, 产生责任链
 */
@Service
public class DefaultChainFactory {
    // 原型模式获取对象
    private final ApplicationContext applicationContext;
    // 存放策略链，策略ID -> 责任链
    private final Map<Long, ILogicChain> strategyChainGroup;
    // 仓储服务
    private final IStrategyRepository repository;
    
    public DefaultChainFactory(ApplicationContext applicationContext, IStrategyRepository repository) {
        this.applicationContext = applicationContext;
        this.strategyChainGroup = new ConcurrentHashMap<>();
        this.repository = repository;
    }
    
    public ILogicChain openLogicChain(Long strategyId) {
        ILogicChain cacheLogicChain = strategyChainGroup.get(strategyId);
        if (cacheLogicChain != null) {
            return cacheLogicChain;
        }
        
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();
        // 如果没有配置 logicChain，那么只装填【默认的责任链节点】
        if (ruleModels == null || ruleModels.length == 0) {
            ILogicChain defaultLogicChain = applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class);
            // 写入缓存
            strategyChainGroup.put(strategyId, defaultLogicChain);
            return defaultLogicChain;
        }
        
        // 首先获取第0个chain
        ILogicChain logicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
        // 之后遍历之后的rule chain
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = applicationContext.getBean(ruleModels[i], ILogicChain.class);
            current = current.appendNext(nextChain);
        }
        // 最后添加一个 default chain
        current.appendNext(applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class));
        // 写入缓存
        strategyChainGroup.put(strategyId, logicChain);
        return logicChain;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 上一个执行的规则模型节点 */
        private String logicModel;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }
    
    @Getter
    @AllArgsConstructor
    public enum LogicModel {
        
        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;
        
        private final String code;
        private final String info;
        
    }
}
