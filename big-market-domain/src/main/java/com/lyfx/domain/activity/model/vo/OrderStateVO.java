package com.lyfx.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午5:18
 * @description
 */

@Getter
@AllArgsConstructor
public enum OrderStateVO {
    completed("completed", "完成");
    
    private final String code;
    private final String desc;
}
