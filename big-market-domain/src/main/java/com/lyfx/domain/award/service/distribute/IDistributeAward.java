package com.lyfx.domain.award.service.distribute;

import com.lyfx.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/5 19:27
 * @description 分发奖品接口
 */


public interface IDistributeAward {
    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);
}
