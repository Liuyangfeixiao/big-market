package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RaffleActivityAccountMonth;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午6:04
 * @description 抽奖活动账户表-月次数
 */

@Mapper
public interface IRaffleActivityAccountMonthDao {
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth build);
    
    void insertActivityAccountMonth(RaffleActivityAccountMonth build);
    
    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth build);
    
    void addAccountQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);
}
