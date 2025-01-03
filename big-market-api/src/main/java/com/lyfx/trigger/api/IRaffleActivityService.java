package com.lyfx.trigger.api;

import com.lyfx.trigger.api.dto.ActivityDrawRequestDTO;
import com.lyfx.trigger.api.dto.ActivityDrawResponseDTO;
import com.lyfx.types.model.Response;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/27 下午4:30
 * @description 抽奖活动服务
 */

public interface IRaffleActivityService {
    /**
     * 活动装配、数据预热缓存
     * @param activityId
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);
    
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);
    
    /**
     * 日历签到返利接口
     * @param userId 用户ID
     * @return 签到状态
     */
    Response<Boolean> calendarSignRebate(String userId);
}
