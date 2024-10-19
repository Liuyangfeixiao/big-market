package com.lyfx.infrastructure.persistent.po;
import lombok.Data;
import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @description 规则树实体对象
 * @time 2024-10-18 17:18
 */

@Data
public class RuleTree{
    /**
    * 自增ID
    */
    private Long id;
    /**
    * 规则树ID
    */
    private String treeId;
    /**
    * 规则树名称
    */
    private String treeName;
    /**
    * 规则树描述
    */
    private String treeDesc;
    /**
    * 规则树根入口规则
    */
    private String treeRootRuleKey;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}
