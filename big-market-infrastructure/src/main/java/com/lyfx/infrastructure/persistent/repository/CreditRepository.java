package com.lyfx.infrastructure.persistent.repository;

import com.lyfx.domain.award.model.vo.AccountStatusVO;
import com.lyfx.domain.credit.model.aggregate.TradeAggregate;
import com.lyfx.domain.credit.model.entity.CreditAccountEntity;
import com.lyfx.domain.credit.model.entity.CreditOrderEntity;
import com.lyfx.domain.credit.repository.ICreditRepository;
import com.lyfx.infrastructure.persistent.dao.IUserCreditAccountDao;
import com.lyfx.infrastructure.persistent.dao.IUserCreditOrderDao;
import com.lyfx.infrastructure.persistent.po.UserCreditAccount;
import com.lyfx.infrastructure.persistent.po.UserCreditOrder;
import com.lyfx.infrastructure.persistent.redis.IRedisService;
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
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/6 12:06
 * @description 积分领域仓储实现
 */

@Slf4j
@Repository
public class CreditRepository implements ICreditRepository {
    
    @Resource
    private IRedisService redisService;
    @Resource
    private IUserCreditOrderDao userCreditOrderDao;
    @Resource
    private IUserCreditAccountDao userCreditAccountDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    
    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
        BigDecimal availableAmount = BigDecimal.ZERO;
        if (userCreditAccount != null) {
            availableAmount = userCreditAccount.getAvailableAmount();
        }
        
        return CreditAccountEntity.builder().userId(userId).adjustAmount(availableAmount).build();
    }
    
    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        // TODO taskEntity 的获取
        
        // 积分账户转换
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount());
        userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount());
        userCreditAccountReq.setAccountStatus(AccountStatusVO.open.getCode());
        
        // 积分订单流水转换
        UserCreditOrder userCreditOrderReq = new UserCreditOrder();
        userCreditOrderReq.setUserId(userId);
        userCreditOrderReq.setOrderId(creditOrderEntity.getOrderId());
        userCreditOrderReq.setTradeAmount(creditOrderEntity.getTradeAmount());
        userCreditOrderReq.setTradeName(creditOrderEntity.getTradeName().getName());
        userCreditOrderReq.setTradeType(creditOrderEntity.getTradeType().getCode());
        userCreditOrderReq.setOutBusinessNo(creditOrderEntity.getOutBusinessNo());
        
        // TODO Task 转换
        
        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId +
                Constants.UNDERLINE + creditAccountEntity.getUserId() +
                Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try{
            lock.lock(3, TimeUnit.SECONDS);
            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 保存账户积分
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    BigDecimal availableAmount = userCreditAccountReq.getAvailableAmount();
                    if (null == userCreditAccount ) {
                        if (availableAmount.compareTo(BigDecimal.ZERO) < 0) {
                            // 没有开通账户，并且消耗积分
                            status.setRollbackOnly();
                            throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
                        } else {
                            userCreditAccountDao.insert(userCreditAccountReq);
                        }
                        
                    } else {
                        if (availableAmount.compareTo(BigDecimal.ZERO) >= 0) {
                            // 增长积分
                            userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                        } else {
                            // 减少积分
                            int subtractCount = userCreditAccountDao.updateSubtractAmount(userCreditAccountReq);
                            if (subtractCount != 1) {
                                // 没有更新，积分可能不足以扣减
                                status.setRollbackOnly();
                                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
                            }
                        }
                    }
                    // 2. 保存用户积分流水订单
                    userCreditOrderDao.insert(userCreditOrderReq);
                    // TODO 写入 task
                    
                    
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整积分额度账户异常. 唯一索引冲突 userId: {} orderId: {}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户额度积分失败. userId: {} orderId: {}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        
        // TODO 发送 MQ 消息
        
    }
}
