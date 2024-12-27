package com.lyfx.domain.award.model.aggregate;

import com.lyfx.domain.award.model.entity.TaskEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 上午11:58
 * @description 用户中奖记录聚合对象【聚合代表一个事务操作】
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {
    private UserAwardRecordEntity userAwardRecordEntity;
    private TaskEntity taskEntity;
}
