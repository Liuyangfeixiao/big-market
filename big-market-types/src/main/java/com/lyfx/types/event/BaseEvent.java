package com.lyfx.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/22 下午7:09
 * @description 基础事件
 */

@Data
public abstract class BaseEvent<T> {
    
    public abstract EventMessage<T> buildEventMessage(T data);
    
    public abstract String topic();
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventMessage<T> {
        private String id;
        private Date timestamp;
        private T data;
    }
}
