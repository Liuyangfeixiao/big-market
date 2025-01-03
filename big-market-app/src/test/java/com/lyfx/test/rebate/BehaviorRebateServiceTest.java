package com.lyfx.test.rebate;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.rebate.model.entity.BehaviorEntity;
import com.lyfx.domain.rebate.model.vo.BehaviorTypeVO;
import com.lyfx.domain.rebate.service.BehaviorRebateService;
import groovy.util.logging.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 22:11
 * @description
 */

@lombok.extern.slf4j.Slf4j
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BehaviorRebateServiceTest {
    @Resource
    private BehaviorRebateService rebateService;
    
    @Test
    public void test_createOrder() throws InterruptedException {
        BehaviorEntity behaviorEntity = new BehaviorEntity();
        behaviorEntity.setUserId("xiaofuge");
        behaviorEntity.setBehaviorType(BehaviorTypeVO.SIGN);
        // 重复的 outBusinessNo 会报错唯一索引字段，保证幂等
        behaviorEntity.setOutBusinessNo("20240105");
        
        List<String> orderIds = rebateService.createOrder(behaviorEntity);
        log.info("请求参数: {}", JSON.toJSONString(behaviorEntity));
        log.info("测试结果: {}", JSON.toJSONString(orderIds));
        
        new CountDownLatch(1).await();
    }
}
