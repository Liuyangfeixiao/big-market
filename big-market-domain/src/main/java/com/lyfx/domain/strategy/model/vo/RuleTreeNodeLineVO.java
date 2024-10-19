package com.lyfx.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:15
 * @description
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeNodeLineVO {
    private String treeId;
    private String ruleNodeFrom;
    private String ruleNodeTo;
    /** 限定类型 1: = ; 2: > ; 3: < ; 4: >= ; 5: <= ; 6: enum[枚举范围] */
    private RuleLimitTypeVO ruleLimitType;
    /** 限定值(到下个节点) */
    private RuleLogicCheckTypeVO ruleLimitValue;
}
