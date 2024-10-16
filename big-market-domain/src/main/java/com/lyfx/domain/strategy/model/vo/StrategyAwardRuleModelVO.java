package com.lyfx.domain.strategy.model.vo;

import com.lyfx.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
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

    public String[] raffleCenterRuleModelList() {
        List<String> ruleModelList = new ArrayList<>();
        String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
        for (String ruleModelValue : ruleModelValues) {
            // 判断是否是center规则
            if (DefaultLogicFactory.LogicModel.isCenter(ruleModelValue)) {
                ruleModelList.add(ruleModelValue);
            }
        }
        // java8之后使用空数组初始化更有效
        return ruleModelList.toArray(new String[0]);
    }

    public String[] raffleAfterRuleModelList() {
        List<String> ruleModelList = new ArrayList<>();
        String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
        for (String ruleModelValue : ruleModelValues) {
            if (DefaultLogicFactory.LogicModel.isAfter(ruleModelValue)) {
                ruleModelList.add(ruleModelValue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    }
}
