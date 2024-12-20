package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午9:23
 * @description
 */

@Service
public class RaffleActivityService extends AbstractRaffleActivity{
    
    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }
}
