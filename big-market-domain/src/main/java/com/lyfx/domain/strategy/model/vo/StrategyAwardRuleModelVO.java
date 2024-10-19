package com.lyfx.domain.strategy.model.vo;

import com.lyfx.types.common.Constants;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyfx
 * @description 抽奖策略规则规则值对象；值对象，没有唯一ID，仅限于从数据库查询对象
 * @time 2024-10-15
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {
    private String ruleModels;
    
}
