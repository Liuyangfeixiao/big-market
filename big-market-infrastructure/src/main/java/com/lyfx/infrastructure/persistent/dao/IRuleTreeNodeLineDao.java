package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RuleTreeNode;
import com.lyfx.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/18 下午7:38
 * @description
 */
@Mapper
public interface IRuleTreeNodeLineDao {
    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);
}
