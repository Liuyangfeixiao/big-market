package com.lyfx.domain.activity.model.entity;

import com.lyfx.domain.activity.model.vo.OrderTradeTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午4:24
 * @description sku充值实体对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkuRechargeEntity {
    /** 用户ID */
    private String userId;
    /** 商品SKU - activity + activity count */
    private Long sku;
    /**
     * 幂等业务单号，外部谁充值谁透传，保证幂等（多次调用也能确保结果唯一，不会多次充值）
     */
    private String outBusinessNo;
    
    private OrderTradeTypeVO orderTradeType = OrderTradeTypeVO.rebate_no_pay_trade;
}
