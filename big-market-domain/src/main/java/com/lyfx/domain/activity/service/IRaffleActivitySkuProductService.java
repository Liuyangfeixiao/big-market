package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.model.entity.SkuProductEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/8 17:47
 * @description
 */

public interface IRaffleActivitySkuProductService {
    List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId);
}
