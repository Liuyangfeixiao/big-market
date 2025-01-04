package com.lyfx.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/4 17:41
 * @description
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleStrategyRuleWeightResponseDTO {
    private Integer ruleWeightCount;
    // 用户完成了多少次抽奖
    private Integer userActivityAccountTotalUseCount;
    
    private List<StrategyAward> strategyAwards;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAward {
        private Integer awardId;
        private String awardTitle;
    }
}
