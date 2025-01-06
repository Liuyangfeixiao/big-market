package com.lyfx.domain.credit.service;

import com.lyfx.domain.credit.model.aggregate.TradeAggregate;
import com.lyfx.domain.credit.model.entity.CreditAccountEntity;
import com.lyfx.domain.credit.model.entity.CreditOrderEntity;
import com.lyfx.domain.credit.model.entity.TradeEntity;
import com.lyfx.domain.credit.model.vo.TradeTypeVO;
import com.lyfx.domain.credit.repository.ICreditRepository;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 15:21
 * @description
 */

@Slf4j
@Service
public class CreditAdjustService implements ICreditAdjustService {
    
    @Resource
    private ICreditRepository creditRepository;
    
    @Override
    public String createOrder(TradeEntity tradeEntity) {
        log.info("创建账户积分额度订单 START. userId: {} tradeName: {} amount: {}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());
        // 0. 判断是否是【逆向交易】扣减积分
        // 需要查询账户是否存在以及积分额度是否充足
        if (TradeTypeVO.REVERSE.equals(tradeEntity.getTradeType())) {
            CreditAccountEntity creditAccountEntity = creditRepository.queryUserCreditAccount(tradeEntity.getUserId());
            if (creditAccountEntity == null || creditAccountEntity.getAdjustAmount().compareTo(tradeEntity.getAmount()) < 0) {
                // 如果账户不存在，或者可用积分小于要扣减的积分，则抛出异常
                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
            }
        }
        
        // 创建积分账户实体
        CreditAccountEntity creditAccountEntity = TradeAggregate.createCreditAccountEntity(tradeEntity.getUserId(), tradeEntity.getAmount());
        
        // 创建积分订单实体
        CreditOrderEntity creditOrderEntity = TradeAggregate.createCreditOrderEntity(
                tradeEntity.getUserId(),
                tradeEntity.getTradeName(),
                tradeEntity.getTradeType(),
                tradeEntity.getAmount(),
                tradeEntity.getOutBusinessNo());
        
        // 构建交易聚合对象
        TradeAggregate tradeAggregate = TradeAggregate.builder()
                .userId(tradeEntity.getUserId())
                .creditAccountEntity(creditAccountEntity)
                .creditOrderEntity(creditOrderEntity)
                .build();
        
        // 保存积分交易订单流水
        creditRepository.saveUserCreditTradeOrder(tradeAggregate);
        log.info("创建用户积分额度订单 FINISHED. userId: {} orderId: {}", tradeEntity.getUserId(), creditOrderEntity.getOrderId());
        
        return creditOrderEntity.getOrderId();
    }
}
