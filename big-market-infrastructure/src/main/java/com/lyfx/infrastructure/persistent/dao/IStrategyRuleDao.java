package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2/5/2024 下午8:41
 * @description
 */
@Mapper
public interface IStrategyRuleDao {
    List<StrategyRule> queryStrategyRuleList();
}
