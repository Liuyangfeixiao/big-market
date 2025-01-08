package com.lyfx.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/8 17:48
 * @description
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkuProductEntity {
    /**
     * 商品sku - 把每一个组合当做一个商品
     */
    private Long sku;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 活动sku次数配置 ID
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
     * 商品金额【积分】
     */
    private BigDecimal productAmount;
    
    /**
     * 商品的具体配置
     */
    private ActivityCount activityCount;
    
    @Data
    public static class ActivityCount {
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
    }
}
