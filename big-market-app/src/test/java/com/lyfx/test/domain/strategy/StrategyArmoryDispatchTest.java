package com.lyfx.test.domain.strategy;

import com.lyfx.domain.strategy.service.armory.IStrategyArmory;
import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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
public class StrategyArmoryDispatchTest {
    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IStrategyDispatch strategyDispatch;
    
    @Before
    public void test_strategyArmory() {
        strategyArmory.assembleLotteryStrategy(100001L);
    }
    
    @Test
    public void test_getAssembleRandomAward() {
        log.info("测试结果: {} - 奖品ID", strategyDispatch.getRandomAwardId(100001L));
        log.info("测试结果: {} - 奖品ID", strategyDispatch.getRandomAwardId(100001L));
        log.info("测试结果: {} - 奖品ID", strategyDispatch.getRandomAwardId(100001L));
    }
    
    @Test
    public void test_getRandomAward_withRuleWeight() {
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(100001L, "4000"));
        log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(100001L, "5000"));
        log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(100001L, "6000"));
    }
}
