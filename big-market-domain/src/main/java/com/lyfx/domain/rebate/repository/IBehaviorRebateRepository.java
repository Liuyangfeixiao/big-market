package com.lyfx.domain.rebate.repository;

import com.lyfx.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.lyfx.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.lyfx.domain.rebate.model.vo.BehaviorTypeVO;
import com.lyfx.domain.rebate.model.vo.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 17:48
 * @description 行为返利仓储接口
 */
public interface IBehaviorRebateRepository {
    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorType);
    
    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);
    
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);
}
