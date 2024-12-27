package com.lyfx.domain.award.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 上午10:52
 * @description
 */
@Getter
@AllArgsConstructor
public enum AwardStateVO {
    create("create", "创建"),
    completed("completed", "发奖完成"),
    fail("fail", "发奖失败"),
    ;
    
    private final String code;
    private final String desc;
}
