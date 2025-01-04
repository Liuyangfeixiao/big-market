package com.lyfx.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/4 14:31
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityAccountResponseDTO {
    /**
     * 总次数
     */
    private Integer totalCount;
    /**
     * 总次数-剩余
     */
    private Integer totalCountSurplus;
    /**
     * 日次数
     */
    private Integer dayCount;
    /**
     * 日次数 - 剩余
     */
    private Integer dayCountSurplus;
    /**
     * 月次数
     */
    private Integer monthCount;
    /**
     * 月次数 - 剩余
     */
    private Integer monthCountSurplus;
}
