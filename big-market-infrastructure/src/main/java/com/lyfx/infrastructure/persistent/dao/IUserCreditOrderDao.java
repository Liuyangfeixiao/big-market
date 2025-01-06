package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.UserCreditOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 12:01
 * @description 用户积分流水单
 */

@Mapper
public interface IUserCreditOrderDao {
    void insert(UserCreditOrder userCreditOrderReq);
}
