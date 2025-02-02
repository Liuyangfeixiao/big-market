package com.lyfx.domain.award.service;

import com.lyfx.domain.award.event.SendAwardMessageEvent;
import com.lyfx.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.lyfx.domain.award.model.entity.DistributeAwardEntity;
import com.lyfx.domain.award.model.entity.TaskEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;
import com.lyfx.domain.award.model.vo.TaskStateVO;
import com.lyfx.domain.award.repository.IAwardRepository;
import com.lyfx.domain.award.service.distribute.IDistributeAward;
import com.lyfx.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午12:00
 * @description
 */

@Slf4j
@Service
public class AwardService implements IAwardService {
    
    private final IAwardRepository awardRepository;
    
    private final SendAwardMessageEvent sendAwardMessageEvent;
    
    // 一个接口，多个实现类
    private final Map<String, IDistributeAward> distributeAwardMap;
    
    public AwardService(IAwardRepository awardRepository, SendAwardMessageEvent sendAwardMessageEvent, Map<String, IDistributeAward> distributeAwardMap) {
        this.awardRepository = awardRepository;
        this.sendAwardMessageEvent = sendAwardMessageEvent;
        this.distributeAwardMap = distributeAwardMap;
    }
    
    
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 构建消息实体对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessageBody = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessageBody.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessageBody.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        sendAwardMessageBody.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessageBody.setOrderId(userAwardRecordEntity.getOrderId());
        sendAwardMessageBody.setAwardConfig(userAwardRecordEntity.getAwardConfig());
        
        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessageBody);
        
        // 构建任务对象
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(eventMessage.getId());
        taskEntity.setMessage(eventMessage);
        taskEntity.setState(TaskStateVO.create);
        
        // 构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();
        // 存储聚合对象-一个事务下，用户的中奖记录
        awardRepository.saveUserAwardRecord(userAwardRecordAggregate);
        log.info("中奖记录保存 FINISHED. userId: {} orderId: {}", userAwardRecordEntity.getUserId(), userAwardRecordEntity.getOrderId());
    }
    
    @Override
    public void distributeAward(DistributeAwardEntity distributeAwardEntity) {
        // 查找 奖品的 key
        String awardKey = awardRepository.queryAwardKey(distributeAwardEntity.getAwardId());
        if (awardKey == null) {
            log.error("分发奖品，奖品ID不存在. awardId: {}", distributeAwardEntity.getAwardId());
            return;
        }
        
        // 奖品服务
        IDistributeAward distributeAward = distributeAwardMap.get(awardKey);
        if (distributeAward == null) {
            log.error("分发奖品，对应的奖品分发服务不存在. awardKey: {}", awardKey);
            throw new RuntimeException("分发奖品, 奖品 " + distributeAwardEntity.getAwardId() + " 对应的服务 awardKey: " + awardKey + " 不存在");
            
        }
        
        // 发放奖品
        distributeAward.giveOutPrizes(distributeAwardEntity);
    }
}
