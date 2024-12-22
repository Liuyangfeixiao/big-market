package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.model.entity.ActivityCountEntity;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.rule.factory.DefaultActivityChainFactory;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午4:44
 * @description 抽奖活动支撑类
 */
public class RaffleActivitySupport {
    protected IActivityRepository activityRepository;
    protected DefaultActivityChainFactory defaultActivityChainFactory;
    
    public RaffleActivitySupport(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }
    
    public ActivitySkuEntity queryActivitySku(Long skuId) {
        return activityRepository.queryActivitySku(skuId);
    }
    
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }
    
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }
}
