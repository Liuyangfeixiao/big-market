package com.lyfx.domain.activity.service.partake;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.lyfx.domain.activity.model.entity.ActivityEntity;
import com.lyfx.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.lyfx.domain.activity.model.entity.UserRaffleOrderEntity;
import com.lyfx.domain.activity.model.vo.ActivityStateVO;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.domain.activity.service.IRaffleActivityPartakeService;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午9:46
 * @description 抽奖活动参与抽象类
 */

@Slf4j
public abstract class AbstractRaffleActivityPartake implements IRaffleActivityPartakeService {
    protected final IActivityRepository activityRepository;
    
    public AbstractRaffleActivityPartake(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    @Override
    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        // 0. 基础信息
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDate = new Date();
        
        // 1. 活动查询，基础信息校验
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);
        
        // 1.1 校验活动状态
        if (!ActivityStateVO.open.equals(activityEntity.getState())) {
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 1.2 校验活动日期
        if (activityEntity.getBeginDateTime().after(currentDate) || activityEntity.getEndDateTime().before(currentDate)) {
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        
        // 2. 查询未被使用的活动参与订单记录
        UserRaffleOrderEntity userRaffleOrderEntity = activityRepository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if (userRaffleOrderEntity != null) {
            log.info("创建参与抽奖活动订单[已存在未消费] userId: {} activityId: {} userRaffleOrderEntity: {}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));
            return userRaffleOrderEntity;
        }
        
        // 3. 用户账户抽奖额度过滤-返回账户构建对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = this.doFilterAccount(userId, activityId, currentDate);
        
        // 4. 构建订单
        UserRaffleOrderEntity userRaffleOrder = this.buildUserRaffleOrder(userId, activityId, currentDate);
        
        // 5. 填充抽奖订单实体类
        createPartakeOrderAggregate.setUserRaffleOrderEntity(userRaffleOrder);
        
        // 6. 保存聚合对象-一个领域内的一个聚合是一个事务操作
        activityRepository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);
        
        // 7. 返回订单信息
        return userRaffleOrder;
    }
    
    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);
    
    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate);
    
    
}
