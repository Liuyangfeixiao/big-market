package com.lyfx.domain.activity.service.quota.policy.impl;

import com.lyfx.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.lyfx.domain.activity.model.vo.OrderStateVO;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/7 14:59
 * @description
 */

@Service("rebate_no_pay_trade")
public class RebateNoPayTradePolicy implements ITradePolicy {
    
    private final IActivityRepository activityRepository;
    
    public RebateNoPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        // 不需支付则修改订单金额为0，状态为 completed，直接给用户充值
        createQuotaOrderAggregate.setOrderState(OrderStateVO.completed);
        createQuotaOrderAggregate.getActivityOrderEntity().setPayAmount(BigDecimal.ZERO);
        
        activityRepository.doSaveNoPayOrder(createQuotaOrderAggregate);
    }
}
