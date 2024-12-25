package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.domain.activity.model.entity.ActivityAccountEntity;
import com.lyfx.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/19 下午9:02
 * @description 抽奖活动账户表
 */

@Mapper
public interface IRaffleActivityAccountDao {
    
    void insert(RaffleActivityAccount raffleActivityAccount);
    
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);
    
    int updateActivityAccountSubtractionQuota(RaffleActivityAccount build);
    
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccount build);
    
    void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount build);
    
    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccount build);
    
    void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount build);
    
    RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount build);
}
