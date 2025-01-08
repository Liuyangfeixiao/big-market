package com.lyfx.domain.activity.service.quota.policy.impl;

import com.lyfx.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.lyfx.domain.activity.model.vo.OrderStateVO;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Service;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/7 14:59
 * @description
 */

@Service("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {
    
    private final IActivityRepository activityRepository;
    
    public CreditPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        // 插入活动账户流水，不改变用户账户额度
        // 等到积分支付完成 ，发送MQ消息，再改变用户账户额度，以及订单状态
        createQuotaOrderAggregate.setOrderState(OrderStateVO.wait_pay);
        activityRepository.doSaveCreditPayOrder(createQuotaOrderAggregate);
    }
}
