package com.lyfx.domain.strategy.service.rule;

import com.lyfx.domain.strategy.model.entity.RuleActionEntity;
import com.lyfx.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午12:09
 * @description
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
