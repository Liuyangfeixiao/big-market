package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/18 下午7:37
 * @description 规则树节点表Dao
 */
@Mapper
public interface IRuleTreeNodeDao {
    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);
}
