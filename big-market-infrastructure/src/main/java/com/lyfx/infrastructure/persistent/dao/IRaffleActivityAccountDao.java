package com.lyfx.infrastructure.persistent.dao;

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
    
}
