package com.lyfx.domain.strategy.service.rule.tree.factory;

import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.model.vo.RuleTreeVO;
import com.lyfx.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.lyfx.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.lyfx.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:53
 * @description 规则树工厂, 提供规则树节点的装配和引擎决策调用
 */

@Service
public class DefaultTreeFactory {
    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;
    
    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }
    // 提供规则引擎
    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID-内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }
}
