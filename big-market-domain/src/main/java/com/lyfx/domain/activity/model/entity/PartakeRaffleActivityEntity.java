package com.lyfx.domain.activity.model.entity;

import lombok.Data;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/25 下午1:17
 * @description 参与抽奖的实体对象
 */

@Data
public class PartakeRaffleActivityEntity {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 活动Id
     */
    private Long activityId;
}
