package com.lyfx.domain.activity.repository;

import com.lyfx.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.lyfx.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.lyfx.domain.activity.model.entity.*;
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
    
    void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate);
    
    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);
    
    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);
    
    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);
    
    ActivitySkuStockKeyVO takeQueueValue(Long sku);
    
    void clearQueueValue(Long sku);
    
    void updateActivitySkuStock(Long sku);
    
    void clearActivitySkuStock(Long sku);
    
    List<Long> querySkuList();
    
    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);
    
    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);
    
    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
    
    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);
    
    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);
    
    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);
    
    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);
}
