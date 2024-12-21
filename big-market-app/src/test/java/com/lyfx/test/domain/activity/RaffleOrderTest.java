package com.lyfx.test.domain.activity;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.activity.model.entity.ActivityOrderEntity;
import com.lyfx.domain.activity.model.entity.ActivityShopCarEntity;
import com.lyfx.domain.activity.model.entity.SkuRechargeEntity;
import com.lyfx.domain.activity.service.IRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午9:27
 * @description 活动抽奖订单测试
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleOrderTest {
    @Resource
    private IRaffleOrder raffleOrder;
    
    @Test
    public void test_createRaffleOrder() {
        ActivityShopCarEntity activityShopCarEntity = new ActivityShopCarEntity();
        activityShopCarEntity.setSku(9011L);
        activityShopCarEntity.setUserId("lyfx");
        ActivityOrderEntity raffleActivityOrder = raffleOrder.createRaffleActivityOrder(activityShopCarEntity);
        log.info("测试结果: {}", JSON.toJSONString(raffleActivityOrder));
    }
    
    @Test
    public void test_createSkuRechargeOrder() {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("lyfx");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009113");
        String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果：{}", orderId);
    }
    
}
