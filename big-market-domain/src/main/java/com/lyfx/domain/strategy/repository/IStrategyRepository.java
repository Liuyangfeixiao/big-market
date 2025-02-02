package com.lyfx.domain.strategy.repository;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;
import com.lyfx.domain.strategy.model.entity.StrategyEntity;
import com.lyfx.domain.strategy.model.entity.StrategyRuleEntity;
import com.lyfx.domain.strategy.model.vo.RuleTreeVO;
import com.lyfx.domain.strategy.model.vo.RuleWeightVO;
import com.lyfx.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.lyfx.domain.strategy.model.vo.StrategyAwardStockKeyVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 策略服务仓储接口
 * @create 2024-05-24 00:03
 */

public interface IStrategyRepository {
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    int getRateRange(Long strategyId);
    
    int getRateRange(String key);
    
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);
    
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);
    
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);
    
    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId);
    
    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);
    
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);
    
    Boolean subtractAwardStock(String cacheKey);
    
    Boolean subtractAwardStock(String cacheKey, Date endDateTime);
    
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);
    
    StrategyAwardStockKeyVO takeQueueValue();
    
    void updateStrategyAwardStock(Long strategyId, Integer awardId);
    
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);
    
    Long queryStrategyIdByActivityId(Long activityId);
    
    Integer queryTodayUserRaffleCount(String userId, Long strategyId);
    
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);
    
    Integer queryActivityAccountTotalUseCount(String userId, Long strategyId);
    
    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);
}
