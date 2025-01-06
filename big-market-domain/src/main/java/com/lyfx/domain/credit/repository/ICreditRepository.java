package com.lyfx.domain.credit.repository;

import com.lyfx.domain.credit.model.aggregate.TradeAggregate;
import com.lyfx.domain.credit.model.entity.CreditAccountEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 12:06
 * @description 积分领域仓储
 */

public interface ICreditRepository {
    CreditAccountEntity queryUserCreditAccount(String userId);
    
    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);
}
