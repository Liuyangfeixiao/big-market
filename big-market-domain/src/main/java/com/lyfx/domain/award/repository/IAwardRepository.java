package com.lyfx.domain.award.repository;

import com.lyfx.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 上午11:57
 * @description
 */
public interface IAwardRepository {
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);
}
