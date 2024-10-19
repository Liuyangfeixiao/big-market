package com.lyfx.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/18 下午5:57
 * @description 规则树节点之间的连线实体
 */

@Data
public class RuleTreeNodeLine {
    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;
    /** 规则Key节点 From */
    private String ruleNodeFrom;
    /** 规则Key节点 To*/
    private String ruleNodeTo;
    /** 限定类型 1: = , 2: > , 3: < , 4: >= , 5: <= , 6: enum[枚举范围]*/
    private String ruleLimitType;
    /** 通往下一个节点的逻辑判断值 */
    private String ruleLimitValue;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
