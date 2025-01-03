package com.lyfx.domain.rebate.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 18:17
 * @description
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {
    create("create", "创建"),
    completed("completed", "发送完成"),
    fail("fail", "发送失败"),
    ;
    
    private final String code;
    private final String desc;
    
}
