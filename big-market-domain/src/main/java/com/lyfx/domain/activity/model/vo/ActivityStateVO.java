package com.lyfx.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午5:16
 * @description 活动状态值对象
 */

@Getter
@AllArgsConstructor
public enum ActivityStateVO {
    create("create", "创建"),
    open("open", "开启"),
    close("close", "关闭"),
    ;
    
    private final String code;
    private final String desc;
}
