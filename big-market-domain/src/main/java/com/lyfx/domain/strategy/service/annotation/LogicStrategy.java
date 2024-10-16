package com.lyfx.domain.strategy.service.annotation;

import com.lyfx.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午5:46
 * @description 策略自定义枚举
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {
    // 定义参数 logicMode
    DefaultLogicFactory.LogicModel logicMode();
}
