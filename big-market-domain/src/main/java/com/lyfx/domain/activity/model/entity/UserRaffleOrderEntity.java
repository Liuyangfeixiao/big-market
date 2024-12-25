package com.lyfx.domain.activity.model.entity;

import com.lyfx.domain.activity.model.vo.UserRaffleOrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/25 下午1:43
 * @description 用户抽奖单实体对象啊
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRaffleOrderEntity {
    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 活动名称 */
    private String activityName;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 订单ID */
    private String orderId;
    /** 下单时间 */
    private Date orderTime;
    /** 订单状态；create-创建、used-已使用、cancel-已作废 */
    private UserRaffleOrderStateVO orderState;
    /** 结束时间 */
    private Date endDateTime;
}
