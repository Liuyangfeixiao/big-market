package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/19 下午9:06
 * @description 抽奖活动次数配置表
 */
@Mapper
public interface IRaffleActivityCountDao {
    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);
}
