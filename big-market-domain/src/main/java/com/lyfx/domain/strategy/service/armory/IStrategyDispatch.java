package com.lyfx.domain.strategy.service.armory;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 4/5/2024 下午8:01
 * @description
 */
public interface IStrategyDispatch {
    Integer getRandomAwardId(Long strategyId);
    
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);
    
    Boolean subtractAwardStock(Long strategyId, Integer awardId, Date endDateTime);
}
