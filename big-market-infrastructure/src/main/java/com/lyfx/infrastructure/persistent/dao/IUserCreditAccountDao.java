package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.UserCreditAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/5 18:06
 * @description 用户积分账户
 */

@Mapper
public interface IUserCreditAccountDao {
    void insert(UserCreditAccount userCreditAccount);
    
    int updateAddAmount(UserCreditAccount userCreditAccount);
}
