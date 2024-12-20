package com.lyfx.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午3:40
 * @description 抽奖活动sku的持久化对象
 */
@Data
public class RaffleActivitySku {
    /**
     * 自增ID
     */
    private Long id;
    
    /**
     * 商品sku - 把每一个组合当做一个商品
     */
    private Long sku;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 活动个人参与次数ID
     */
    private Long activityCountId;
    
    /**
     * 商品库存
     */
    private Integer stockCount;
    
    /**
     * 剩余库存
     */
    private Integer stockCountSurplus;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}
