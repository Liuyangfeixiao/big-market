package com.lyfx.domain.strategy.model.vo;

import lombok.*;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/4 19:43
 * @description
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleWeightVO {
    private String ruleValue;
    
    private Integer weight;
    
    private List<Award> awardList;
    
    private List<Integer> awardIds;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Award {
        private Integer awardId;
        private String awardTitle;
    }
}
