package com.lyfx.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/18 上午11:32
 * @description 抽奖活动单 持久化对象
 */
@Data
public class RaffleActivityOrder {
    /**
     * 自增ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 商品Sku
     */
    private Long sku;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 活动名称
     */
    private String activityName;
    
    /**
     * 抽奖策略ID
     */
    private Long strategyId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 下单时间
     */
    private Date orderTime;
    
    /**
     * 订单状态（completed）
     */
    private String state;
    
    /**
     * 总次数
     */
    private Integer totalCount;
    
    /**
     * 日次数
     */
    private Integer dayCount;
    
    /**
     * 月次数
     */
    private Integer monthCount;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}
