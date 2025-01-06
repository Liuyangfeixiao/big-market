package com.lyfx.domain.credit.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 12:10
 * @description
 */

@Getter
@AllArgsConstructor
public enum TradeTypeVO {
    FORWARD("forward", "正向交易 +积分"),
    REVERSE("reverse", "逆向交易 -积分"),
    ;
    
    private final String code;
    private final String info;
}
