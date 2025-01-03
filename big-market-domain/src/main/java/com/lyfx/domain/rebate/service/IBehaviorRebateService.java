package com.lyfx.domain.rebate.service;

import com.lyfx.domain.rebate.model.entity.BehaviorEntity;
import com.lyfx.domain.rebate.model.entity.BehaviorRebateOrderEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 17:41
 * @description 行为返利接口
 */
public interface IBehaviorRebateService {
    List<String> createOrder(BehaviorEntity behavior);
    
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);
}
