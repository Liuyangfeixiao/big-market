package com.lyfx.trigger.api.dto;

import lombok.Data;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/23 下午10:09
 * @description 抽奖奖品列表，请求对象
 */

@Data
public class RaffleAwardListRequestDTO {
    // 抽奖策略ID
    @Deprecated
    private Long strategyId;
    // 活动ID
    private Long activityId;
    // 用户ID
    private String userId;
}
