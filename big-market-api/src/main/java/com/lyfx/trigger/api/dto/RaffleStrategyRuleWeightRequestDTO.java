package com.lyfx.trigger.api.dto;

import lombok.Data;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/4 17:40
 * @description
 */

@Data
public class RaffleStrategyRuleWeightRequestDTO {
    // 用户ID
    private String userId;
    // 活动ID
    private Long activityId;
}
