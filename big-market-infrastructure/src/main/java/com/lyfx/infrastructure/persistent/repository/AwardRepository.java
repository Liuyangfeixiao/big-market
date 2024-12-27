package com.lyfx.infrastructure.persistent.repository;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.lyfx.domain.award.model.entity.TaskEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;
import com.lyfx.domain.award.repository.IAwardRepository;
import com.lyfx.infrastructure.event.EventPublisher;
import com.lyfx.infrastructure.persistent.dao.ITaskDao;
import com.lyfx.infrastructure.persistent.dao.IUserAwardRecordDao;
import com.lyfx.infrastructure.persistent.po.Task;
import com.lyfx.infrastructure.persistent.po.UserAwardRecord;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午4:04
 * @description 奖品仓储服务
 */

@Slf4j
@Repository
public class AwardRepository implements IAwardRepository {
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IUserAwardRecordDao userAwardRecordDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;
    
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
        
        transactionTemplate.execute(status -> {
           try {
               // 写入记录
               userAwardRecordDao.insert(userAwardRecord);
               // 写入任务
               taskDao.insert(task);
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
        } catch (Exception e) {
            // 如果消息发送失败，则将task设置为fail，走任务扫描兜底
            log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }
}
