package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/19 下午9:07
 * @description 抽奖活动订单Dao
 */
@Mapper
public interface IRaffleActivityOrderDao {
    
    void insert(RaffleActivityOrder raffleActivityOrder);
    
    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);
    
    RaffleActivityOrder queryRaffleActivityOrder(RaffleActivityOrder raffleActivityOrderReq);
    
    int updateOrderCompleted(RaffleActivityOrder raffleActivityOrderReq);
    
    RaffleActivityOrder queryUnpaidActivityOrder(RaffleActivityOrder raffleActivityOrderReq);
}
