package com.lyfx.domain.activity.service.quota.rule;

import com.lyfx.domain.activity.model.entity.ActivityCountEntity;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午4:51
 * @description 下单规则过滤接口
 */
public interface IActionChain extends IActionArmory {
    boolean action(ActivityEntity activityEntity, ActivitySkuEntity activitySkuEntity, ActivityCountEntity activityCountEntity);
}
