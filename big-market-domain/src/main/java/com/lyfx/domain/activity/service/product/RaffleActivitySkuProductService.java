package com.lyfx.domain.activity.service.product;

import com.lyfx.domain.activity.model.entity.ActivitySkuEntity;
import com.lyfx.domain.activity.model.entity.SkuProductEntity;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.IRaffleActivitySkuProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/8 17:52
 * @description
 */

@Service
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {
    
    @Resource
    private IActivityRepository activityRepository;
    
    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        
        return activityRepository.querySkuProductEntityListByActivityId(activityId);
    }
}
