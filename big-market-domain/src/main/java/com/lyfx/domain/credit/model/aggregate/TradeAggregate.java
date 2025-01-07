package com.lyfx.domain.credit.model.aggregate;

import com.lyfx.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.lyfx.domain.credit.model.entity.CreditAccountEntity;
import com.lyfx.domain.credit.model.entity.CreditOrderEntity;
import com.lyfx.domain.credit.model.entity.TaskEntity;
import com.lyfx.domain.credit.model.vo.TaskStateVO;
import com.lyfx.domain.credit.model.vo.TradeNameVO;
import com.lyfx.domain.credit.model.vo.TradeTypeVO;
import com.lyfx.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 15:10
 * @description 积分交易聚合对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeAggregate {
    // 用户ID
    private String userId;
    // 积分账户实体
    private CreditAccountEntity creditAccountEntity;
    // 积分订单实体
    private CreditOrderEntity creditOrderEntity;
    
    // 消息事件实体
    private TaskEntity taskEntity;
    
    public static CreditAccountEntity createCreditAccountEntity(String userId, BigDecimal adjustAmount) {
        return CreditAccountEntity.builder().userId(userId).adjustAmount(adjustAmount).build();
    }
    
    public static CreditOrderEntity createCreditOrderEntity(String userId,
                                                            TradeNameVO tradeName,
                                                            TradeTypeVO tradeType,
                                                            BigDecimal tradeAmount,
                                                            String outBusinessNo) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .orderId(RandomStringUtils.randomNumeric(12))
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(tradeAmount)
                .outBusinessNo(outBusinessNo)
                .build();
    }
    
    public static TaskEntity createTaskEntity(String userId, String topic, String messageId, BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userId);
        taskEntity.setTopic(topic);
        taskEntity.setMessageId(messageId);
        taskEntity.setMessage(message);
        taskEntity.setState(TaskStateVO.create);
        return taskEntity;
    }
    
}
