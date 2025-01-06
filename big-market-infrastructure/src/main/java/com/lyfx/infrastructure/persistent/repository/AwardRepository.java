package com.lyfx.infrastructure.persistent.repository;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.lyfx.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.lyfx.domain.award.model.entity.TaskEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;
import com.lyfx.domain.award.model.entity.UserCreditAwardEntity;
import com.lyfx.domain.award.model.vo.AccountStatusVO;
import com.lyfx.domain.award.repository.IAwardRepository;
import com.lyfx.infrastructure.event.EventPublisher;
import com.lyfx.infrastructure.persistent.dao.*;
import com.lyfx.infrastructure.persistent.po.Task;
import com.lyfx.infrastructure.persistent.po.UserAwardRecord;
import com.lyfx.infrastructure.persistent.po.UserCreditAccount;
import com.lyfx.infrastructure.persistent.po.UserRaffleOrder;
import com.lyfx.infrastructure.persistent.redis.RedisService;
import com.lyfx.types.common.Constants;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午4:04
 * @description 奖品仓储服务
 */

@Slf4j
@Repository
public class AwardRepository implements IAwardRepository {
    @Resource
    private IAwardDao awardDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IUserAwardRecordDao userAwardRecordDao;
    @Resource
    private IUserRaffleOrderDao userRaffleOrderDao;
    @Resource
    private IUserCreditAccountDao userCreditAccountDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;
    @Resource
    private RedisService redisService;
    
    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate) {
        // 1. 取出数据
        UserAwardRecordEntity userAwardRecordEntity = userAwardRecordAggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = userAwardRecordAggregate.getTaskEntity();
        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();
        
        UserAwardRecord userAwardRecord = new UserAwardRecord();
        userAwardRecord.setUserId(userAwardRecordEntity.getUserId());
        userAwardRecord.setActivityId(userAwardRecordEntity.getActivityId());
        userAwardRecord.setStrategyId(userAwardRecordEntity.getStrategyId());
        userAwardRecord.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecord.setAwardId(userAwardRecordEntity.getAwardId());
        userAwardRecord.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        userAwardRecord.setAwardTime(userAwardRecordEntity.getAwardTime());
        userAwardRecord.setAwardState(userAwardRecordEntity.getAwardState().getCode());
        
        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());
        
        UserRaffleOrder userRaffleOrderReq = new UserRaffleOrder();
        userRaffleOrderReq.setUserId(userId);
        userRaffleOrderReq.setActivityId(activityId);
        userRaffleOrderReq.setOrderId(userAwardRecordEntity.getOrderId());
        
        transactionTemplate.execute(status -> {
            try {
                // 写入记录
                userAwardRecordDao.insert(userAwardRecord);
                // 写入任务
                taskDao.insert(task);
                // 更新用户活动订单状态为 used
                int count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userRaffleOrderReq);
                if (count != 1) {
                    status.setRollbackOnly();
                    log.error("写入中奖记录，用户抽奖单已使用过，不可重复抽奖 userId: {} activityId: {} awardId: {}", userId, activityId, awardId);
                    throw new AppException(ResponseCode.ACTIVITY_ORDER_ERROR.getCode(), ResponseCode.ACTIVITY_ORDER_ERROR.getInfo());
                }
                return 1;
            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                log.error("写入中奖记录，唯一索引冲突 userId: {} activityId: {} awardId: {}", userId, activityId, awardId, e);
                throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
            }
        });
        
        try {
            // 发送MQ消息【在事务外执行，如果失败还有任务补偿】
            eventPublisher.publish(taskEntity.getTopic(), task.getMessage());
            // 更新数据库记录，task 任务表
            taskDao.updateTaskSendMessageCompleted(task);
            log.info("写入中奖记录，发送MQ消息成功. userId: {} orderId: {} topic: {}", userId, userAwardRecord.getOrderId(), task.getTopic());
        } catch (Exception e) {
            // 如果消息发送失败，则将task设置为fail，走任务扫描兜底
            log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }
    
    @Override
    public String queryAwardConfig(Integer awardId) {
        return awardDao.queryAwardConfigByAwardId(awardId);
    }
    
    @Override
    public void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate) {
        String userId = giveOutPrizesAggregate.getUserId();
        UserCreditAwardEntity userCreditAwardEntity = giveOutPrizesAggregate.getUserCreditAwardEntity();
        UserAwardRecordEntity userAwardRecordEntity = giveOutPrizesAggregate.getUserAwardRecordEntity();
        
        // 更新发奖记录
        UserAwardRecord userAwardRecordReq = new UserAwardRecord();
        userAwardRecordReq.setUserId(userId);
        userAwardRecordReq.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecordReq.setAwardState(userAwardRecordEntity.getAwardState().getCode());
        
        // 更新用户积分 「首次则插入数据」
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userCreditAwardEntity.getUserId());
        userCreditAccountReq.setTotalAmount(userCreditAwardEntity.getCreditAmount());
        userCreditAccountReq.setAvailableAmount(userCreditAwardEntity.getCreditAmount());
        userCreditAccountReq.setAccountStatus(AccountStatusVO.open.getCode());
        
        RLock lock = redisService.getLock(Constants.RedisKey.ACTIVITY_ACCOUNT_LOCK+userId);
        try{
            lock.lock(3, TimeUnit.SECONDS);
            
            transactionTemplate.execute(status -> {
                try {
                    // 更新积分 or 创建积分账户
                    UserCreditAccount userCreditAccountRes = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    
                    if (userCreditAccountRes == null) {
                        // 没有账户存在，创建账户
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        // 更新账户积分
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }
                    
                    // 更新奖品发放状态
                    Integer updateAwardCount = userAwardRecordDao.updateAwardRecordCompletedState(userAwardRecordReq);
                    if (0 == updateAwardCount) {
                        log.warn("更新奖品记录，重复更新拦截 userId:{} giveOutPrizesAggregate:{}", userId, JSON.toJSONString(giveOutPrizesAggregate));
                        status.setRollbackOnly();
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("更新奖品记录，唯一索引冲突 userId: {}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        
    }
    
    @Override
    public String queryAwardKey(Integer awardId) {
        return awardDao.queryAwardKeyByAwardId(awardId);
    }
}
