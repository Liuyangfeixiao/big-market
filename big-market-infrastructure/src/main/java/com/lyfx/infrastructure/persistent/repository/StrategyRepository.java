package com.lyfx.infrastructure.persistent.repository;

import com.lyfx.domain.strategy.model.entity.StrategyAwardEntity;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StrategyRepository implements IStrategyRepository {
    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        return null;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {

    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey) {
        return null;
    }

    @Override
    public int getRateRange(Long strategyId) {
        return 0;
    }
}
