package com.lyfx.infrastructure.persistent.repository;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;
import com.lyfx.domain.strategy.model.entity.StrategyEntity;
import com.lyfx.domain.strategy.model.entity.StrategyRuleEntity;
import com.lyfx.domain.strategy.model.vo.*;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.infrastructure.persistent.dao.*;
import com.lyfx.infrastructure.persistent.po.*;
import com.lyfx.infrastructure.persistent.redis.IRedisService;
import com.lyfx.types.common.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyRuleDao strategyRuleDao;
    @Resource
    private IStrategyAwardDao strategyAwardDao;
    @Resource
    private IRedisService redisService;
    
    @Resource
    private IRuleTreeDao ruleTreeDao;
    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;
    
    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        // 先从redis中读取数据
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (null != strategyAwardEntities && !strategyAwardEntities.isEmpty()) {
            return strategyAwardEntities;
        }
        // redis中没有找到，则使用数据库获取
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        // 设置redis缓存
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {
        // 1. 存储抽奖策略奖品范围值
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key, rateRange);
        // 存储奖品查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+key);
        cacheRateTable.putAll(strategyAwardSearchRateTable);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        // 从redis中得到随机值代表的奖品
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+key, rateKey);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }
    
    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key);
    }
    
    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 1. 优先从缓存中获取数据
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (null != strategyEntity) return strategyEntity;
        // 2. 从数据库中获取数据
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity =  StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        // 将strategyEntity放入缓存中
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }
    
    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        if (null == strategyRuleRes) return null;
        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();
        
    }
    
    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValues(strategyRule);
    }
    
    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }
    
    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModel(strategyAward);
        return StrategyAwardRuleModelVO.builder().ruleModels(ruleModels).build();
    }
    
    @Override
    public RuleTreeVO queryRuleTreeVOByTreeId(String treeId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        RuleTreeVO ruleTreeVO = redisService.getValue(cacheKey);
        if (null != ruleTreeVO) return ruleTreeVO;
        
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLines = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);
        
        // treeNodeLine 转换为 Map 结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineVOMap = new HashMap<>();
        for (RuleTreeNodeLine ruleTreeNodeLine : ruleTreeNodeLines) {
            // 转换为 VO
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(treeId)
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();
            // 每个节点所延申的边
            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineVOMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }
        
        // treeNode 转化为 Map 结构
        Map<String, RuleTreeNodeVO> treeNodeMap = new HashMap<>();
        for (RuleTreeNode ruleTreeNode : ruleTreeNodes) {
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(treeId)
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineVOMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            treeNodeMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }
        
        // 构造 Rule Tree
        RuleTreeVO ruleTreeVODB = RuleTreeVO.builder()
                .treeId(treeId)
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeRootRuleKey())
                .treeNodeMap(treeNodeMap)
                .build();
        
        // TODO 设置key的过期时间为活动结束时间
        redisService.setValue(cacheKey, ruleTreeVODB);
        
        return ruleTreeVODB;
    }
}
