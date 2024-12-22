package com.lyfx.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/22 下午6:45
 * @description 活动sku库存 key值对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySkuStockKeyVO {
    /*商品SKU*/
    private Long sku;
    /*活动ID*/
    private Long activityId;
}
