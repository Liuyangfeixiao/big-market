package com.lyfx.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.strategy.model.vo.RuleTreeVO;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/18 下午9:54
 * @description 仓储数据查询
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {
    @Resource
    private IStrategyRepository repository;
    @Test
    public void queryRuleTreeVOByTreeId() {
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果: {}", JSON.toJSONString(ruleTreeVO));
    }
    
}
