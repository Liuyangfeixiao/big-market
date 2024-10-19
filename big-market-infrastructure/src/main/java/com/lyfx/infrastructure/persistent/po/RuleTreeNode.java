package com.lyfx.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/18 下午5:19
 * @description 规则树节点实体对象
 */

@Data
public class RuleTreeNode {
    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;
    /** 规则树名称 */
    private String ruleKey;
    /** 规则节点描述 */
    private String ruleDesc;
    /** 规则节点值 */
    private String ruleValue;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
