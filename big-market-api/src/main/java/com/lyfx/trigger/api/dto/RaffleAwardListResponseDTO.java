package com.lyfx.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/23 下午10:10
 * @description
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponseDTO {
    // 奖品ID
    private Integer awardId;
    // 奖品标题
    private String awardTitle;
    // 奖品副标题【抽奖一次后解锁】
    private String awardSubtitle;
    // 排序编号
    private Integer sort;
    // 奖品次数规则-抽奖n次后解锁，未配置则为空
    private Integer awardRuleLockCount;
    // 奖品是否解锁-true 已解锁，false 未解锁
    private Boolean isAwardUnlock;
    // 等待解锁次数 - [规定的抽奖次数-用户已经抽奖的次数]
    private Integer waitUnlockCount;
}
