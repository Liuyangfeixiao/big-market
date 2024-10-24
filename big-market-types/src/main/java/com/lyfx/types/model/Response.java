package com.lyfx.types.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/23 下午10:05
 * @description 相应返回
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {
    
    private String code;
    private String info;
    private T data;
    
}