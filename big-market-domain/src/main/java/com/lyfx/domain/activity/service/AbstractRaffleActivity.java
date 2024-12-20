package com.lyfx.domain.activity.service;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.activity.model.entity.*;
import com.lyfx.domain.activity.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午6:13
 * @description 抽奖活动抽象类，定义抽奖标准流程
 */

@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder {
    
    protected IActivityRepository activityRepository;
    
    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCarEntity activityShopCarEntity) {
        // 1. 通过sku查询商品活动信息
        ActivitySkuEntity activitySkuEntity =  activityRepository.queryActivitySku(activityShopCarEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        
        log.info("查询结果: {} {} {}", JSON.toJSONString(activityEntity), JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityCountEntity));
        
        return ActivityOrderEntity.builder().build();
    }
}
