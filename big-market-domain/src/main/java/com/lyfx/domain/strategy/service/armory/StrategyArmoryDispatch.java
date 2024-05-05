package com.lyfx.domain.strategy.service.armory;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;
import com.lyfx.domain.strategy.model.entity.StrategyEntity;
import com.lyfx.domain.strategy.model.entity.StrategyRuleEntity;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {
    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1. 查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);
        // 正常装配全量数据
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);
        // 2. 权重策略配置 - 适用于 rule_weight 权重规则配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight(); // 其实就是 "rule_weight" 字符串
        if (ruleWeight == null) return true;
        
        StrategyRuleEntity strategyRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);
        if (null == strategyRuleEntity) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),
                    ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keySet = ruleWeightValueMap.keySet();
        // 更新缓存中的不同权重对应的奖品装配
        for (String key : keySet) {
            List<Integer> values = ruleWeightValueMap.get(key);
            // 保留不同权重对应的奖品ID
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !values.contains(entity.getAwardId()));
            // 装配每个权重对应的奖品范围
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }
        return true;
    }
    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
        // 1. 获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        // 2. 循环计算找到概率值的范围
        BigDecimal rateRange = BigDecimal.valueOf(convert(minAwardRate.doubleValue()));
        
        // 3. 生成策略奖品查找表「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
        List<Integer> strategyAwardSearchTable = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            // 计算出每种奖品需要填充的数量, 舍弃小数位
            for (int i = 0; i < rateRange.multiply(awardRate).intValue(); i++) {
                strategyAwardSearchTable.add(awardId);
            }
        }
        
        // 4. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchTable);
        
        // 5. 生成Map集合，key的范围是随机值的范围，value是奖品ID
        Map<Integer, Integer> strategyAwardSearchMap = new LinkedHashMap<>();
        for (int i = 0; i < strategyAwardSearchTable.size(); ++i) {
            strategyAwardSearchMap.put(i, strategyAwardSearchTable.get(i));
        }
        
        // 6. 将Map集合存入redis中，方便之后查找
        // 这里使用strategyAwardSearchTable.size()而非rateRange
        // 是防止之后生成的随机数在table中对应为null
        repository.storeStrategyAwardSearchRateTable(key, strategyAwardSearchTable.size(), strategyAwardSearchMap);
    }
    /**
     * 转换计算，只根据小数位来计算。如【0.01返回100】、【0.009返回1000】、【0.0018返回10000】
     */
    private double convert(double min) {
        double current = min;
        double max = 1;
        while (current < 1) {
            current = current * 10;
            max = max * 10;
        }
        return max;
    }
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下，不一定为当前应用做的策略装配。
        // 也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        // 从redis中获取策略奖品范围
        int rateRange = repository.getRateRange(strategyId);
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }
    
    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        // 从redis中获取当前策略和当前积分权重对应的随机数范围
        int rateRange = repository.getRateRange(key);
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }
}
