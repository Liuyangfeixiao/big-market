package com.lyfx.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/27 下午5:42
 * @description 活动抽奖请求对象
 */

@Data
public class ActivityDrawRequestDTO implements Serializable {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 活动ID
     */
    private Long activityId;
}
