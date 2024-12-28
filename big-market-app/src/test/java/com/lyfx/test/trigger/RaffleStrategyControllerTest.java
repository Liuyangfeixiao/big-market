package com.lyfx.test.trigger;

import com.alibaba.fastjson2.JSON;
import com.lyfx.trigger.api.IRaffleStrategyService;
import com.lyfx.trigger.api.dto.RaffleAwardListRequestDTO;
import com.lyfx.trigger.api.dto.RaffleAwardListResponseDTO;
import com.lyfx.trigger.http.RaffleStrategyController;
import com.lyfx.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/28 下午8:42
 * @description 营销抽奖服务测试
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyControllerTest {
    
    @Resource
    private IRaffleStrategyService raffleStrategyService;
    
    @Test
    public void test_queryRaffleAwardList() {
        RaffleAwardListRequestDTO request = new RaffleAwardListRequestDTO();
        request.setUserId("xiaofuge");
        request.setActivityId(100301L);
        Response<List<RaffleAwardListResponseDTO>> response = raffleStrategyService.queryRaffleAwardList(request);
        
        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));
        
    }
    
}
