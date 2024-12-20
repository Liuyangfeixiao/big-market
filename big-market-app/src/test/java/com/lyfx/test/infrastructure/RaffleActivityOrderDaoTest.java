package com.lyfx.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.lyfx.infrastructure.persistent.dao.IRaffleActivityOrderDao;
import com.lyfx.infrastructure.persistent.po.RaffleActivityOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 上午10:38
 * @description 抽奖活动订单测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityOrderDaoTest {
    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;
    
    private final EasyRandom easyRandom = new EasyRandom();
    
    @Test
    public void test_insert() {
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        raffleActivityOrder.setUserId("lyfx");
        raffleActivityOrder.setActivityId(100301L);
        raffleActivityOrder.setActivityName("测试活动");
        raffleActivityOrder.setStrategyId(100006L);
        raffleActivityOrder.setOrderId(RandomStringUtils.randomAlphanumeric(12));
        raffleActivityOrder.setOrderTime(new Date());
        raffleActivityOrder.setState("not_used");
        // 插入数据
        raffleActivityOrderDao.insert(raffleActivityOrder);
    }
    
    @Test
    public void test_insert_random() {
        for (int i = 0; i < 50; ++i) {
            RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
            raffleActivityOrder.setUserId(easyRandom.nextObject(String.class));
            raffleActivityOrder.setActivityId(100301L);
            raffleActivityOrder.setActivityName("测试活动");
            raffleActivityOrder.setStrategyId(100006L);
            raffleActivityOrder.setOrderId(RandomStringUtils.randomAlphanumeric(12));
            raffleActivityOrder.setOrderTime(new Date());
            raffleActivityOrder.setState("not_used");
            raffleActivityOrderDao.insert(raffleActivityOrder);
        }
    }
    @Test
    public void test_queryRaffleActivityOrderByUserId() {
        String userId = "lyfx";
        List<RaffleActivityOrder> raffleActivityOrders = raffleActivityOrderDao.queryRaffleActivityOrderByUserId(userId);
        log.info("测试结果: {}", JSON.toJSONString(raffleActivityOrders));
    }
}
