package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.UserAwardRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午6:03
 * @description 用户中奖记录
 */

@Mapper
public interface IUserAwardRecordDao {
    void insert(UserAwardRecord userAwardRecord);
}
