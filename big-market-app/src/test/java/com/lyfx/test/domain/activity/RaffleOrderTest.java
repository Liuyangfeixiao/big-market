package com.lyfx.test.domain.activity;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.activity.model.entity.ActivityOrderEntity;
import com.lyfx.domain.activity.model.entity.ActivityShopCarEntity;
import com.lyfx.domain.activity.model.entity.SkuRechargeEntity;
import com.lyfx.domain.activity.service.IRaffleOrder;
import com.lyfx.domain.activity.service.armory.IActivityArmory;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

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
    @Resource
    private IActivityArmory activityArmory;
    
    @Before
    public void setUp() {
        log.info("装配活动: {}", activityArmory.assembleActivitySku(9011L));
    }
    
    @Test
    public void test_createRaffleOrder() {
        ActivityShopCarEntity activityShopCarEntity = new ActivityShopCarEntity();
        activityShopCarEntity.setSku(9011L);
        activityShopCarEntity.setUserId("lyfx");
        ActivityOrderEntity raffleActivityOrder = raffleOrder.createRaffleActivityOrder(activityShopCarEntity);
        log.info("测试结果: {}", JSON.toJSONString(raffleActivityOrder));
    }
    
    @Test
    public void test_createSkuRechargeOrder_duplicate() {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("lyfx");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009113");
        String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果：{}", orderId);
    }
    
    /**
     * 测试库存消耗和最终一致更新
     * 1. raffle_activity_sku 库表库存可以设置20个
     * 2. 清空 redis 缓存 flushall
     * 3. for 循环20次，消耗完库存，最终数据库剩余库存为0
     */
    @Test
    public void test_createSkuRechargeOrder() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            try {
                SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                skuRechargeEntity.setUserId("lyfx");
                skuRechargeEntity.setSku(9011L);
                // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
                skuRechargeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(12));
                String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
                log.info("测试结果：{}", orderId);
            } catch (AppException e) {
                log.warn(e.getInfo());
            }
        }
        
        new CountDownLatch(1).await();
    }
    
    
}
