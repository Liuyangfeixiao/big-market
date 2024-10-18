package com.lyfx.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/17 上午11:20
 * @description 规则限定枚举值
 */

@Getter
@AllArgsConstructor
public enum RuleLimitTypeVO {
    EQUAL(1, "等于"),
    GT(2, "大于"),
    LT(3, "小于"),
    GE(4, "大于&等于"),
    LE(5, "小于&等于"),
    ENUM(6, "枚举"),
    ;
    
    private final Integer code;
    private final String info;
}
