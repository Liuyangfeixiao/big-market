package com.lyfx.domain.award.service;

import com.lyfx.domain.award.model.entity.DistributeAwardEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 上午10:49
 * @description 奖品服务接口
 */

public interface IAwardService {
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);
    
    void distributeAward(DistributeAwardEntity distributeAwardEntity);
}
