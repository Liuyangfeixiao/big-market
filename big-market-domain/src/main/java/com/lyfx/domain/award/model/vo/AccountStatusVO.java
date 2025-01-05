package com.lyfx.domain.award.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/5 20:19
 * @description
 */

@Getter
@AllArgsConstructor
public enum AccountStatusVO {
    open("open", "开启"),
    close("close", "冻结"),
    ;
    
    private final String code;
    private final String desc;
}
