package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.model.aggregate.CreateOrderAggregate;
import com.lyfx.domain.activity.model.entity.*;
import com.lyfx.domain.activity.model.vo.ActivitySkuStockKeyVO;
import com.lyfx.domain.activity.model.vo.OrderStateVO;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import com.lyfx.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import com.lyfx.domain.strategy.service.IRaffleStock;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午9:23
 * @description
 */

@Service
public class RaffleActivityService extends AbstractRaffleActivity implements ISkuStock {
    
    public RaffleActivityService(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        super(defaultActivityChainFactory, activityRepository);
    }
    
    @Override
    protected void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
        activityRepository.doSaveOrder(createOrderAggregate);
    }
    
    @Override
    protected CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        // 订单实体对象
        ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
        activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
        activityOrderEntity.setSku(skuRechargeEntity.getSku());
        activityOrderEntity.setActivityId(activityEntity.getActivityId());
        activityOrderEntity.setActivityName(activityEntity.getActivityName());
        activityOrderEntity.setStrategyId(activityEntity.getStrategyId());
        // 公司里一般会有专门的雪花算法UUID服务，我们这里直接生成个12位就可以了。
        activityOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        activityOrderEntity.setOrderTime(new Date());
        activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
        activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
        activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
        activityOrderEntity.setState(OrderStateVO.completed);
        activityOrderEntity.setOutBusinessNo(skuRechargeEntity.getOutBusinessNo());
        
        // 构建聚合对象
        return CreateOrderAggregate.builder()
                .userId(skuRechargeEntity.getUserId())
                .activityId(activitySkuEntity.getActivityId())
                .totalCount(activityCountEntity.getTotalCount())
                .dayCount(activityCountEntity.getDayCount())
                .monthCount(activityCountEntity.getMonthCount())
                .activityOrderEntity(activityOrderEntity)
                .build();
        
    }
    
    @Override
    public ActivitySkuStockKeyVO takeQueueValue(Long sku) throws InterruptedException {
        return activityRepository.takeQueueValue(sku);
    }
    
    @Override
    public void clearQueueValue(Long sku) {
        activityRepository.clearQueueValue(sku);
    }
    
    @Override
    public void updateActivitySkuStock(Long sku) {
        activityRepository.updateActivitySkuStock(sku);
    }
    
    @Override
    public void clearActivitySkuStock(Long sku) {
        activityRepository.clearActivitySkuStock(sku);
    }
    
    @Override
    public List<Long> querySkuList() {
        return activityRepository.querySkuList();
    }
}
