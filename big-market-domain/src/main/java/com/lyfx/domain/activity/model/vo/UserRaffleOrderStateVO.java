package com.lyfx.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/25 下午1:47
 * @description
 */

@Getter
@AllArgsConstructor
public enum UserRaffleOrderStateVO {
    create("create", "创建"),
    used("used", "已使用"),
    cancel("cancel", "已作废")
    ;
    private final String code;
    private final String desc;
}
