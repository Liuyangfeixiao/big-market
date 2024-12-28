package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RuleTree;
import com.lyfx.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/18 下午7:37
 * @description 规则树表Dao
 */
@Mapper
public interface IRuleTreeDao {
    RuleTree queryRuleTreeByTreeId(String treeId);
}
