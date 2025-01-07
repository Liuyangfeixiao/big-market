package com.lyfx.domain.activity.service.quota;

import com.lyfx.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.lyfx.domain.activity.model.entity.*;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.lyfx.domain.activity.service.quota.policy.ITradePolicy;
import com.lyfx.domain.activity.service.quota.rule.IActionChain;
import com.lyfx.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午6:13
 * @description 抽奖活动抽象类，定义抽奖标准流程
 */

@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {
    
    // 不同类型的交易策略实现，通过构造函数注入到 Map 中
    private final Map<String, ITradePolicy> tradePolicyGroup;
    
    public AbstractRaffleActivityAccountQuota(DefaultActivityChainFactory defaultActivityChainFactory,
                                              IActivityRepository activityRepository,
                                              Map<String, ITradePolicy> tradePolicyGroup) {
        super(defaultActivityChainFactory, activityRepository);
        this.tradePolicyGroup = tradePolicyGroup;
    }
    
    @Override
    public String createOrder(SkuRechargeEntity skuRechargeEntity) {
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
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activityEntity, activitySkuEntity, activityCountEntity);
        
        // 4. 构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);
        
        // 5. 根据交易类型选择处理交易方法
        ITradePolicy tradePolicy = tradePolicyGroup.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);
        
        // 6. 返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }
    
//    protected abstract void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate);
    
    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
