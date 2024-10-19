package com.lyfx.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午10:37
 * @description 规则树节点对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeNodeVO {
    /** 规则树ID */
    private String treeId;
    /** 规则key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 规则的值 */
    private String ruleValue;
    /** 规则连线 */
    private List<RuleTreeNodeLineVO> treeNodeLineVOList;
}
