package com.lyfx.domain.rebate.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 17:42
 * @description 行为类型枚举
 */

@Getter
@AllArgsConstructor
public enum BehaviorTypeVO {
    SIGN("sign", "签到"),
    OPENAI_PAY("openai_pay", "openai 外部支付完成"),
    ;
    private final String code;
    private final String info;
    
}
