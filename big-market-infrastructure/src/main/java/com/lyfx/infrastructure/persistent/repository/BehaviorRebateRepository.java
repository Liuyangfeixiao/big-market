package com.lyfx.infrastructure.persistent.repository;

import com.alibaba.fastjson2.JSON;
import com.lyfx.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.lyfx.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.lyfx.domain.rebate.model.entity.TaskEntity;
import com.lyfx.domain.rebate.model.vo.BehaviorTypeVO;
import com.lyfx.domain.rebate.model.vo.DailyBehaviorRebateVO;
import com.lyfx.domain.rebate.repository.IBehaviorRebateRepository;
import com.lyfx.infrastructure.event.EventPublisher;
import com.lyfx.infrastructure.persistent.dao.IDailyBehaviorRebateDao;
import com.lyfx.infrastructure.persistent.dao.ITaskDao;
import com.lyfx.infrastructure.persistent.dao.IUserBehaviorRebateOrderDao;
import com.lyfx.infrastructure.persistent.po.DailyBehaviorRebate;
import com.lyfx.infrastructure.persistent.po.Task;
import com.lyfx.infrastructure.persistent.po.UserBehaviorRebateOrder;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import groovy.util.logging.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 20:29
 * @description 行为返利服务仓储实现
 */

@lombok.extern.slf4j.Slf4j
@Slf4j
@Repository
public class BehaviorRebateRepository implements IBehaviorRebateRepository {
    @Resource
    private IDailyBehaviorRebateDao dailyBehaviorRebateDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IUserBehaviorRebateOrderDao userBehaviorRebateOrderDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;
    
    @Override
    public List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorType) {
        List<DailyBehaviorRebate> dailyBehaviorRebates = dailyBehaviorRebateDao.queryDailyBehaviorRebateByBehaviorType(behaviorType.getCode().toLowerCase());
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS = new ArrayList<>(dailyBehaviorRebates.size());
        
        for (DailyBehaviorRebate dailyBehaviorRebate : dailyBehaviorRebates) {
            dailyBehaviorRebateVOS.add(DailyBehaviorRebateVO.builder()
                    .behaviorType(dailyBehaviorRebate.getBehaviorType())
                    .rebateDesc(dailyBehaviorRebate.getRebateDesc())
                    .rebateType(dailyBehaviorRebate.getRebateType())
                    .rebateConfig(dailyBehaviorRebate.getRebateConfig())
                    .build());
        }
        
        return dailyBehaviorRebateVOS;
    }
    
    @Override
    public void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates) {
        transactionTemplate.execute(status -> {
            try {
                for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
                    BehaviorRebateOrderEntity behaviorRebateOrderEntity = behaviorRebateAggregate.getBehaviorRebateOrderEntity();
                    
                    // 用户行为返利订单对象
                    UserBehaviorRebateOrder userBehaviorRebateOrder = new UserBehaviorRebateOrder();
                    userBehaviorRebateOrder.setUserId(behaviorRebateOrderEntity.getUserId());
                    userBehaviorRebateOrder.setOrderId(behaviorRebateOrderEntity.getOrderId());
                    userBehaviorRebateOrder.setBehaviorType(behaviorRebateOrderEntity.getBehaviorType());
                    userBehaviorRebateOrder.setRebateDesc(behaviorRebateOrderEntity.getRebateDesc());
                    userBehaviorRebateOrder.setRebateConfig(behaviorRebateOrderEntity.getRebateConfig());
                    userBehaviorRebateOrder.setRebateType(behaviorRebateOrderEntity.getRebateType());
                    userBehaviorRebateOrder.setOutBusinessNo(behaviorRebateOrderEntity.getOutBusinessNo());
                    userBehaviorRebateOrder.setBizId(behaviorRebateOrderEntity.getBizId());
                    userBehaviorRebateOrderDao.insert(userBehaviorRebateOrder);
                    
                    // 任务对象
                    TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
                    Task task = new Task();
                    task.setUserId(taskEntity.getUserId());
                    task.setTopic(taskEntity.getTopic());
                    task.setMessageId(taskEntity.getMessageId());
                    task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
                    task.setState(taskEntity.getState().getCode());
                    taskDao.insert(task);
                    
                }
                
                return 1;
            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                log.error("写入返利记录，唯一索引冲突 userId: {}", userId, e);
                throw new AppException(ResponseCode.INDEX_DUP.getCode(), ResponseCode.INDEX_DUP.getInfo());
            }
        });
        
        // 发送MQ消息
        for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
            TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
            Task task = new Task();
            task.setUserId(taskEntity.getUserId());
            task.setMessageId(taskEntity.getMessageId());
            try {
                // 发送消息【在事务外执行，消息发送失败还有补偿任务】
                eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
            } catch (Exception e) {
                log.error("写入返利记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic(), e);
                taskDao.updateTaskSendMessageFail(task);
            }
        }
    }
    
    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        // 1. 请求对象
        UserBehaviorRebateOrder userBehaviorRebateOrderReq = new UserBehaviorRebateOrder();
        userBehaviorRebateOrderReq.setUserId(userId);
        userBehaviorRebateOrderReq.setOutBusinessNo(outBusinessNo);
        // 2. 查询结果
        List<UserBehaviorRebateOrder> userBehaviorRebateOrderResList = userBehaviorRebateOrderDao.queryOrderByOutBusinessNo(userBehaviorRebateOrderReq);
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = new ArrayList<>(userBehaviorRebateOrderResList.size());
        for (UserBehaviorRebateOrder userBehaviorRebateOrder : userBehaviorRebateOrderResList) {
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(userBehaviorRebateOrder.getUserId())
                    .orderId(userBehaviorRebateOrder.getOrderId())
                    .behaviorType(userBehaviorRebateOrder.getBehaviorType())
                    .rebateDesc(userBehaviorRebateOrder.getRebateDesc())
                    .rebateType(userBehaviorRebateOrder.getRebateType())
                    .rebateConfig(userBehaviorRebateOrder.getRebateConfig())
                    .outBusinessNo(userBehaviorRebateOrder.getOutBusinessNo())
                    .bizId(userBehaviorRebateOrder.getBizId())
                    .build();
            behaviorRebateOrderEntities.add(behaviorRebateOrderEntity);
        }
        return behaviorRebateOrderEntities;
        
    }
}
