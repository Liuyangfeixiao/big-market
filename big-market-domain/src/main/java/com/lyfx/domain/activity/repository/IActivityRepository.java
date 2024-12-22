package com.lyfx.domain.activity.repository;

import com.lyfx.domain.activity.model.aggregate.CreateOrderAggregate;
import com.lyfx.domain.activity.model.entity.ActivityCountEntity;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;
import com.lyfx.domain.activity.model.vo.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午7:21
 * @description 活动仓储接口
 */
public interface IActivityRepository {
    ActivitySkuEntity queryActivitySku(Long sku);
    
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);
    
    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);
    
    void doSaveOrder(CreateOrderAggregate createOrderAggregate);
    
    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);
    
    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);
    
    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);
    
    ActivitySkuStockKeyVO takeQueueValue(Long sku);
    
    void clearQueueValue(Long sku);
    
    void updateActivitySkuStock(Long sku);
    
    void clearActivitySkuStock(Long sku);
    
    List<Long> querySkuList();
}
