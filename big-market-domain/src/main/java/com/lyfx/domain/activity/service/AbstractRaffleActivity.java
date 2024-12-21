package com.lyfx.domain.activity.service;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.activity.model.aggregate.CreateOrderAggregate;
import com.lyfx.domain.activity.model.entity.*;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.rule.IActionChain;
import com.lyfx.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午6:13
 * @description 抽奖活动抽象类，定义抽奖标准流程
 */

@Slf4j
public abstract class AbstractRaffleActivity extends RaffleQActivitySupport implements IRaffleOrder {
    
    public AbstractRaffleActivity(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        super(defaultActivityChainFactory, activityRepository);
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
    
    @Override
    public String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity) {
        // 1. 参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (sku == null || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        // 2. 查询基础信息
        // 2.1 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        // 2.2 查询活动信息
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        
        // 3. 活动动作规则校验
        // TODO 后续处理规则过滤流程，暂时不处理责任链结果
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        boolean success = actionChain.action(activityEntity, activitySkuEntity, activityCountEntity);
        
        // 4. 构建订单聚合对象
        CreateOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);
        // 5. 保存订单
        doSaveOrder(createOrderAggregate);
        // 6. 返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }
    
    protected abstract void doSaveOrder(CreateOrderAggregate createOrderAggregate);
    
    protected abstract CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
