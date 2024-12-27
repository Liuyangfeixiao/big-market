package com.lyfx.domain.award.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 上午11:10
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
