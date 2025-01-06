package com.lyfx.test.trigger;

import com.alibaba.fastjson2.JSON;
import com.lyfx.trigger.api.IRaffleActivityService;
import com.lyfx.trigger.api.dto.ActivityDrawRequestDTO;
import com.lyfx.trigger.api.dto.ActivityDrawResponseDTO;
import com.lyfx.trigger.api.dto.UserActivityAccountRequestDTO;
import com.lyfx.trigger.api.dto.UserActivityAccountResponseDTO;
import com.lyfx.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/28 下午9:35
 * @description
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityControllerTest {
    @Resource
    private IRaffleActivityService activityService;
    
    @Test
    public void test_armory() {
        
        Response<Boolean> response = activityService.armory(100301L);
        log.info("测试结果：{}", JSON.toJSONString(response));
    }
    
    @Test
    public void test_draw() {
        ActivityDrawRequestDTO requestDTO = new ActivityDrawRequestDTO();
        requestDTO.setActivityId(100301L);
        requestDTO.setUserId("xiaofuge");
        Response<ActivityDrawResponseDTO> response = activityService.draw(requestDTO);
        
        log.info("测试请求: {}", JSON.toJSONString(requestDTO));
        log.info("测试结果: {}", JSON.toJSONString(response));
    }
    
    @Test
    public void test_draw_blacklist() throws InterruptedException {
        ActivityDrawRequestDTO requestDTO = new ActivityDrawRequestDTO();
        requestDTO.setActivityId(100301L);
        requestDTO.setUserId("user003");
        Response<ActivityDrawResponseDTO> response = activityService.draw(requestDTO);
        
        log.info("测试请求: {}", JSON.toJSONString(requestDTO));
        log.info("测试结果: {}", JSON.toJSONString(response));
        
        new CountDownLatch(1).await();
    }
    
    @Test
    public void test_calendarSignRebate() throws InterruptedException {
        Response<Boolean> response = activityService.calendarSignRebate("lyfx");
        log.info("测试结果: {}", JSON.toJSONString(response));
        new CountDownLatch(1).await();
    }
    
    @Test
    public void test_isCalendarRebate() {
        Response<Boolean> response = activityService.isCalendarSignRebate("lyfx");
        log.info("测试结果: {}", JSON.toJSONString(response));
    }
    
    @Test
    public void test_queryUserActivityAccount() {
        UserActivityAccountRequestDTO request = new UserActivityAccountRequestDTO();
        request.setUserId("lyfx");
        request.setActivityId(100301L);
        // 查询数据
        Response<UserActivityAccountResponseDTO> response = activityService.queryUserActivityAccount(request);
        
        log.info("请求参数: {}", JSON.toJSONString(request));
        log.info("测试结果: {}", JSON.toJSONString(response));
    }
}
