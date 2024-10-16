package com.lyfx.domain.strategy.service.rule.filter.factory;

import com.lyfx.domain.strategy.model.entity.RuleActionEntity;
import com.lyfx.domain.strategy.service.annotation.LogicStrategy;
import com.lyfx.domain.strategy.service.rule.filter.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午5:49
 * @description 规则工厂，用于产生逻辑过滤器
 */
@Service
public class DefaultLogicFactory {
    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();
    
    // 通过构造函数实现自动注入
    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters) {
        logicFilters.forEach(logic -> {
            // 查找annotation注解
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
            if (strategy != null) {
                logicFilterMap.put(strategy.logicMode().getCode(), logic);
            }
        });
    }
    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }
    
    // 1. 先定义逻辑模型枚举
    @Getter
    @AllArgsConstructor
    public enum LogicModel {
        RULE_WEIGHT("rule_weight", "【抽奖前规则】根据抽奖权重返回可抽奖范围KEY", "before"),
        RULE_BLACKLIST("rule_blacklist","【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回", "before"),
        RULE_LOCK("rule_lock", "【抽奖中规则】抽奖n次后，对应奖品可解锁抽奖", "center"),
        RULE_LUCK_AWARD("rule_luck_award", "【抽奖后规则】抽奖n次后，对应奖品可解锁抽奖", "after")
        ;
        private final String code;
        private final String info;
        private final String type;

        public static boolean isCenter(String code) {
            return "center".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

        public static boolean isAfter(String code) {
            return "after".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }
    }
}
