package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2/5/2024 下午8:40
 * @description
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAward> queryStrategyAwardList();
    
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModel(StrategyAward strategyAward);
    
    void updateStrategyAwardStock(StrategyAward strategyAward);
}
