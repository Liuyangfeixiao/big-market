package com.lyfx.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.lyfx.infrastructure.persistent.dao.IRaffleActivityDao;
import com.lyfx.infrastructure.persistent.po.RaffleActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/19 下午11:05
 * @description
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityDaoTest {
    @Resource
    private IRaffleActivityDao raffleActivityDao;
    
    @Test
    public void test_queryRaffleActivityByActivityId() {
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(100301L);
        log.info("测试结果: {}", JSON.toJSONString(raffleActivity));
    }
}
