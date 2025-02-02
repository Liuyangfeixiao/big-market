package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午6:03
 * @description 用户抽奖订单表
 */

@Mapper
public interface IUserRaffleOrderDao {
    void insert(UserRaffleOrder build);
    
    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder build);
    
    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);
}
