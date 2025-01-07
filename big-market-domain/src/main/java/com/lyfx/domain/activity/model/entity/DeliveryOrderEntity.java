package com.lyfx.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/7 16:00
 * @description 发货单实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOrderEntity {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 业务防重ID - 外部透传，返利，行为等唯一标识
     */
    private String outBusinessNo;
}
