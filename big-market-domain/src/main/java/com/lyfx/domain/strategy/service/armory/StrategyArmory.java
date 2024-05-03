package com.lyfx.domain.strategy.service.armory;

import com.lyfx.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory {
    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        return false;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        return null;
    }
}
