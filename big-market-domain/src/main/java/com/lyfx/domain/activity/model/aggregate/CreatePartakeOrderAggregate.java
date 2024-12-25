package com.lyfx.domain.activity.model.aggregate;

import com.lyfx.domain.activity.model.entity.ActivityAccountDayEntity;
import com.lyfx.domain.activity.model.entity.ActivityAccountEntity;
import com.lyfx.domain.activity.model.entity.ActivityAccountMonthEntity;
import com.lyfx.domain.activity.model.entity.UserRaffleOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/25 下午3:45
 * @description 参与活动订单聚合对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePartakeOrderAggregate {
    private String userId;
    private Long activityId;
    private ActivityAccountEntity activityAccountEntity;
    
    private boolean isExistAccountDay = true;
    
    private ActivityAccountDayEntity activityAccountDayEntity;
    
    private boolean isExistAccountMonth = true;
    
    private ActivityAccountMonthEntity activityAccountMonthEntity;
    
    private UserRaffleOrderEntity userRaffleOrderEntity;
}
