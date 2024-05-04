package com.lyfx.domain.strategy.service.armory;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory {
    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1. 查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);
        
        // 2. 获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        // 3. 循环计算找到概率值的范围
        BigDecimal rateRange = BigDecimal.valueOf(convert(minAwardRate.doubleValue()));
        
        // 4. 生成策略奖品查找表「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
        List<Integer> strategyAwardSearchTable = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            // 计算出每种奖品需要填充的数量, 舍弃小数位
            for (int i = 0; i < rateRange.multiply(awardRate).intValue(); i++) {
                strategyAwardSearchTable.add(awardId);
            }
        }
        
        // 5. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchTable);
        
        // 6. 生成Map集合，key的范围是随机值的范围，value是奖品ID
        Map<Integer, Integer> strategyAwardSearchMap = new LinkedHashMap<>();
        for (int i = 0; i < strategyAwardSearchTable.size(); ++i) {
            strategyAwardSearchMap.put(i, strategyAwardSearchTable.get(i));
        }
        
        // 8. 将Map集合存入redis中，方便之后查找
        // 这里使用strategyAwardSearchTable.size()而非rateRange
        // 是防止之后生成的随机数在table中对应为null
        repository.storeStrategyAwardSearchRateTable(strategyId, strategyAwardSearchTable.size(), strategyAwardSearchMap);
        
        return true;
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
        return repository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
