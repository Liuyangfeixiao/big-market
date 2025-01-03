package com.lyfx.domain.rebate.service;

import com.lyfx.domain.rebate.event.SendRebateMessageEvent;
import com.lyfx.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.lyfx.domain.rebate.model.entity.BehaviorEntity;
import com.lyfx.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.lyfx.domain.rebate.model.entity.TaskEntity;
import com.lyfx.domain.rebate.model.vo.DailyBehaviorRebateVO;
import com.lyfx.domain.rebate.model.vo.TaskStateVO;
import com.lyfx.domain.rebate.repository.IBehaviorRebateRepository;
import com.lyfx.types.common.Constants;
import com.lyfx.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 17:47
 * @description 行为返利服务实现
 */

@Service
public class BehaviorRebateService implements IBehaviorRebateService {
    
    @Resource
    private IBehaviorRebateRepository behaviorRebateRepository;
    
    @Resource
    private SendRebateMessageEvent sendRebateMessageEvent;
    
    @Override
    public List<String> createOrder(BehaviorEntity behavior) {
        // 1. 查询返利配置
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS = behaviorRebateRepository.queryDailyBehaviorRebateConfig(behavior.getBehaviorType());
        if (dailyBehaviorRebateVOS == null || dailyBehaviorRebateVOS.isEmpty()) return new ArrayList<>();
        
        // 2. 构建聚合对象
        List<String> orderIds = new ArrayList<>();
        List<BehaviorRebateAggregate> behaviorRebateAggregates = new ArrayList<>();
        for (DailyBehaviorRebateVO dailyBehaviorRebateVO : dailyBehaviorRebateVOS) {
            // 拼装业务ID: userId_rebateType_outBusinessNo
            String bizId = behavior.getUserId() + Constants.UNDERLINE + dailyBehaviorRebateVO.getRebateType()
                    + Constants.UNDERLINE + behavior.getOutBusinessNo();
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(behavior.getUserId())
                    .orderId(RandomStringUtils.randomNumeric(12))
                    .behaviorType(dailyBehaviorRebateVO.getBehaviorType())
                    .rebateDesc(dailyBehaviorRebateVO.getRebateDesc())
                    .rebateType(dailyBehaviorRebateVO.getRebateType())
                    .rebateConfig(dailyBehaviorRebateVO.getRebateConfig())
                    .outBusinessNo(behavior.getOutBusinessNo())
                    .bizId(bizId)
                    .build();
            orderIds.add(behaviorRebateOrderEntity.getOrderId());
            
            // MQ 消息对象
            SendRebateMessageEvent.RebateMessage rebateMessage = SendRebateMessageEvent.RebateMessage.builder()
                    .userId(behavior.getUserId())
                    .rebateConfig(dailyBehaviorRebateVO.getRebateConfig())
                    .rebateType(dailyBehaviorRebateVO.getRebateType())
                    .bizId(bizId)
                    .build();
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> rebateMessageEventMessage = sendRebateMessageEvent.buildEventMessage(rebateMessage);
            
            // 组装任务对象
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(behavior.getUserId());
            taskEntity.setTopic(sendRebateMessageEvent.topic());
            taskEntity.setMessage(rebateMessageEventMessage);
            taskEntity.setMessageId(rebateMessageEventMessage.getId());
            taskEntity.setState(TaskStateVO.create);
            
            BehaviorRebateAggregate behaviorRebateAggregate = BehaviorRebateAggregate.builder()
                    .userId(behavior.getUserId())
                    .behaviorRebateOrderEntity(behaviorRebateOrderEntity)
                    .taskEntity(taskEntity)
                    .build();
            behaviorRebateAggregates.add(behaviorRebateAggregate);
        }
        
        // 3. 存储聚合对象数据
        behaviorRebateRepository.saveUserRebateRecord(behavior.getUserId(), behaviorRebateAggregates);
        
        // 返回订单ID集合
        return orderIds;
        
    }
    
    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        return behaviorRebateRepository.queryOrderByOutBusinessNo(userId, outBusinessNo);
    }
}
