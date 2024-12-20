package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/19 下午9:07
 * @description 抽奖活动表Dao
 */
@Mapper
public interface IRaffleActivityDao {
    RaffleActivity queryRaffleActivityByActivityId(Long activityId);
}
