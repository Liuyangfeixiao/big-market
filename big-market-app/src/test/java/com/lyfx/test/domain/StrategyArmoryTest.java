package com.lyfx.test.domain;

import com.lyfx.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 4/5/2024 下午2:04
 * @description 策略装配进行测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {
    @Resource
    private IStrategyArmory strategyArmory;
    
    @Test
    public void test_strategyArmory() {
        strategyArmory.assembleLotteryStrategy(100002L);
    }
    
    @Test
    public void test_getAssembleRandomAward() {
        log.info("测试结果: {} - 奖品ID", strategyArmory.getRandomAwardId(100002L));
        log.info("测试结果: {} - 奖品ID", strategyArmory.getRandomAwardId(100002L));
        log.info("测试结果: {} - 奖品ID", strategyArmory.getRandomAwardId(100002L));
    }
}
