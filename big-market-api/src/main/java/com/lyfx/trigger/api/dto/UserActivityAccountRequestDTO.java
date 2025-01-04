package com.lyfx.trigger.api.dto;

import lombok.Data;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/4 14:30
 * @description 用户活动账户请求对象
 */

@Data
public class UserActivityAccountRequestDTO {
    private String userId;
    private Long activityId;
}
