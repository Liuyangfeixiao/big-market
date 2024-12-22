package com.lyfx.domain.activity.service.rule.impl;

import com.lyfx.domain.activity.model.entity.ActivityCountEntity;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;
import com.lyfx.domain.activity.model.vo.ActivityStateVO;
import com.lyfx.domain.activity.service.rule.AbstractActionChain;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午5:28
 * @description 活动规则基本信息【日期，状态】
 */

@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivityEntity activityEntity, ActivitySkuEntity activitySkuEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-base_action START. sku: {} activityId: {}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 校验-活动状态
        if (!ActivityStateVO.open.equals(activityEntity.getState())) {
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 校验-活动日期
        Date currentDate = new Date();
        if (activityEntity.getBeginDateTime().after(currentDate) || activityEntity.getEndDateTime().before(currentDate)) {
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        // 校验-活动sku库存 【剩余库存从缓存中获取】
        if (activitySkuEntity.getStockCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }
        
        return next().action(activityEntity, activitySkuEntity, activityCountEntity);
    }
}
