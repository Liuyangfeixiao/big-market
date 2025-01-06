package com.lyfx.domain.credit.service;

import com.lyfx.domain.credit.model.entity.TradeEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 12:08
 * @description
 */
public interface ICreditAdjustService {
    String createOrder(TradeEntity tradeEntity);
}
