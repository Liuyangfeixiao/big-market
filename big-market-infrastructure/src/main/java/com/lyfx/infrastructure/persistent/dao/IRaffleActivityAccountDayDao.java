package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RaffleActivityAccountDay;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午6:03
 * @description 抽奖活动账户表-日次数
 */

@Mapper
public interface IRaffleActivityAccountDayDao {
    RaffleActivityAccountDay queryActivityAccountDayByUserId(RaffleActivityAccountDay raffleActivityAccountDayReq);
    
    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDay build);
    
    void insertActivityAccountDay(RaffleActivityAccountDay build);
    
    Integer queryRaffleActivityAccountDayPartakeCount(RaffleActivityAccountDay raffleActivityAccountDay);
    
    void addAccountQuota(RaffleActivityAccountDay raffleActivityAccountDay);
}
