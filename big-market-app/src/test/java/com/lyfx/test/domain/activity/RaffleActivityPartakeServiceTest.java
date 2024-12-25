package com.lyfx.test.domain.activity;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.lyfx.domain.activity.model.entity.UserRaffleOrderEntity;
import com.lyfx.domain.activity.service.IRaffleActivityPartakeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/25 下午8:51
 * @description 抽奖活动订单测试
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityPartakeServiceTest {
    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    
    @Test
    public void test_createOrder() {
        PartakeRaffleActivityEntity partakeRaffleActivityEntity = new PartakeRaffleActivityEntity();
        partakeRaffleActivityEntity.setUserId("lyfx");
        partakeRaffleActivityEntity.setActivityId(100301L);
        log.info("请求参数: userId: {} activityId: {}", partakeRaffleActivityEntity.getUserId(), partakeRaffleActivityEntity.getActivityId());
        UserRaffleOrderEntity order = raffleActivityPartakeService.createOrder(partakeRaffleActivityEntity);
        
        log.info("测试结果: {}", JSON.toJSONString(order));
    }
}
