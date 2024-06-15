package com.lyfx.test.domain;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.strategy.model.entity.RaffleAwardEntity;
import com.lyfx.domain.strategy.model.entity.RaffleFactorEntity;
import com.lyfx.domain.strategy.service.IRaffleStrategy;
import com.lyfx.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/6/15 下午8:29
 * @description 抽奖策略测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {
    @Resource
    private IRaffleStrategy raffleStrategy;
    
    @Resource
    private RuleWeightLogicFilter ruleWeightLogicFilter;
    
    @Before
    public void setUp() {
        // 通过反射设置 userScore
        ReflectionTestUtils.setField(ruleWeightLogicFilter, "userScore", 4500L);
    }
    @Test
    public void test_performRaffle() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("lyfx")
                .strategyId(100001L)
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }
    
    @Test
    public void test_blacklist() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("user003")  // 黑名单用户 user001,user002,user003
                .strategyId(100001L)
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }
}
