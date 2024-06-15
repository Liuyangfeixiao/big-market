package com.lyfx.domain.strategy.model.entity;

import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午12:14
 * @description 规则动作实体，将会影响到之后的流程变化
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity> {
    
    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    // 过滤的是哪条规则
    private String ruleModel;
    private T data;

    static public class RaffleEntity {
    
    }
    // 抽奖之前
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {
        /** 策略ID */
        private Long strategyId;
        
        private String ruleWeightValueKey;
        
        /** 奖品ID */
        private Integer awardId;
    }
    // 抽奖中
    static public class RaffleDuringEntity extends RaffleEntity {
    
    }
    // 抽奖之后
    static public class RaffleAfterEntity extends RaffleEntity {
    
    }
    
}
