package com.lyfx.domain.activity.service.quota.policy;

import com.lyfx.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/7 14:58
 * @description
 */
public interface ITradePolicy {
    
    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);
}
