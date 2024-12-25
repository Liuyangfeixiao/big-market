package com.lyfx.domain.activity.service.quota.rule.impl;

import com.lyfx.domain.activity.model.entity.ActivityCountEntity;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;
import com.lyfx.domain.activity.model.vo.ActivitySkuStockKeyVO;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.armory.IActivityDispatch;
import com.lyfx.domain.activity.service.quota.rule.AbstractActionChain;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午5:29
 * @description 商品库存规则
 */

@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {
    
    @Resource
    private IActivityDispatch activityDispatch;
    @Resource
    private IActivityRepository activityRepository;
    
    @Override
    public boolean action(ActivityEntity activityEntity, ActivitySkuEntity activitySkuEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-sku_stock_action START. sku: {} activityId: {}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 扣减库存, 这是扣减redis
        boolean status = activityDispatch.subtractionActivitySkuStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());
        
        // 库存扣减成功
        if (status) {
            log.info("活动责任链-sku_stock_action SUCCESS. sku: {} activityId: {}", activitySkuEntity.getSku(), activityEntity.getActivityId());
            
            // 写入延迟队列，延迟消费更新库存记录
            activityRepository.activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO.builder()
                    .sku(activitySkuEntity.getSku())
                    .activityId(activityEntity.getActivityId())
                    .build());
            
            return true;
        }
        
        throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
    }
}
