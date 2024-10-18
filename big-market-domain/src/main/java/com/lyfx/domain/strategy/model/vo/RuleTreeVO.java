package com.lyfx.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午10:32
 * @description 规则树对象 【不具有唯一ID，不需要改变数据库结果的对象，可被定义为值对象】
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeVO {
    /** 规则树ID */
    private Integer treeId;
    /** 规则树名称 */
    private String treeName;
    /** 规则树描述 */
    private String treeDesc;
    /** 规则树的根节点 */
    private String treeRootRuleNode;
    /** 这棵树包含的规则节点 */
    private Map<String, RuleTreeNodeVO> treeNodeMap;
}
