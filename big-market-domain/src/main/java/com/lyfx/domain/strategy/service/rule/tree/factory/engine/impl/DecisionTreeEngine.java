package com.lyfx.domain.strategy.service.rule.tree.factory.engine.impl;

import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.model.vo.RuleTreeNodeLineVO;
import com.lyfx.domain.strategy.model.vo.RuleTreeNodeVO;
import com.lyfx.domain.strategy.model.vo.RuleTreeVO;
import com.lyfx.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.lyfx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.lyfx.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 下午2:54
 * @description 决策树引擎
 */

@Slf4j
public class DecisionTreeEngine implements IDecisionTreeEngine {
    
    private final Map<String, ILogicTreeNode> logicTreeGroup;
    
    private final RuleTreeVO ruleTreeVO;
    
    public DecisionTreeEngine(final Map<String, ILogicTreeNode> logicTreeGroup, RuleTreeVO ruleTreeVO) {
        
        this.logicTreeGroup = logicTreeGroup;
        this.ruleTreeVO = ruleTreeVO;
    }
    
    @Override
    public DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId) {
        DefaultTreeFactory.StrategyAwardVO strategyAwardData = null;
        
        // 获取基础信息
        String nextNode = ruleTreeVO.getTreeRootRuleNode();
        Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();
        
        // 获取起始节点，根节点记录了第一个要执行的规则
        RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
        while (nextNode != null) {
            // 获取决策节点
            ILogicTreeNode logicTreeNode = logicTreeGroup.get(ruleTreeNode.getRuleKey());
            String ruleValue = ruleTreeNode.getRuleValue();
            
            // 决策节点的计算，过滤
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId, ruleValue);
            // 判断返回的状态
            RuleLogicCheckTypeVO ruleLogicCheckType = logicEntity.getRuleLogicCheckType();
            strategyAwardData = logicEntity.getStrategyAwardVO();
            log.info("决策树引擎【{}】 treeId: {} node: {} code: {}",
                    ruleTreeVO.getTreeName(), ruleTreeVO.getTreeId(), nextNode, ruleLogicCheckType.getCode());
            
            // 获取下一个节点
            nextNode = nextNode(ruleLogicCheckType.getCode(), ruleTreeNode.getTreeNodeLineVOList());
            ruleTreeNode = treeNodeMap.get(nextNode);
        }
        
        // 返回最终结果
        return strategyAwardData;
    }
    
    private String nextNode(String code, List<RuleTreeNodeLineVO> treeNodeLineVOList) {
        // 如果没有边了，那么返回
        if (treeNodeLineVOList == null || treeNodeLineVOList.isEmpty()) {return null;}
        // 遍历每一条边，选择相应的边走下去
        for (RuleTreeNodeLineVO nodeLine : treeNodeLineVOList) {
            if (decisionLogic(code, nodeLine)) {
                return nodeLine.getRuleNodeTo();
            }
        }
//        throw new RuntimeException("决策树引擎, nextNode 计算失败，未找到可执行节点");
        return null;
    }
    
    public boolean decisionLogic(String logicCheckCode, RuleTreeNodeLineVO nodeLine) {
        switch (nodeLine.getRuleLimitType()) {
            case EQUAL:
                return logicCheckCode.equals(nodeLine.getRuleLimitValue().getCode());
            // 以下规则暂时不需要实现
            case GE:
            case GT:
            case LE:
            case LT:
            case ENUM:
            default:
                return false;
        }
    }
}
