package com.lyfx.domain.activity.repository;

import com.lyfx.domain.activity.model.entity.ActivityCountEntity;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午7:21
 * @description 活动仓储接口
 */
public interface IActivityRepository {
    ActivitySkuEntity queryActivitySku(Long sku);
    
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);
    
    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);
}
