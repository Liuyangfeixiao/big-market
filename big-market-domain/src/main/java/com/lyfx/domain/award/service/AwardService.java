package com.lyfx.domain.award.service;

import com.lyfx.domain.award.event.SendAwardMessageEvent;
import com.lyfx.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.lyfx.domain.award.model.entity.TaskEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;
import com.lyfx.domain.award.model.vo.TaskStateVO;
import com.lyfx.domain.award.repository.IAwardRepository;
import com.lyfx.types.event.BaseEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午12:00
 * @description
 */

@Service
public class AwardService implements IAwardService {
    @Resource
    private IAwardRepository awardRepository;
    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;
    
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 构建消息实体对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessageBody = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessageBody.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessageBody.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        sendAwardMessageBody.setUserId(userAwardRecordEntity.getUserId());
        
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
    }
}
