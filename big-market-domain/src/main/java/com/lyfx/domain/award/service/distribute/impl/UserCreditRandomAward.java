package com.lyfx.domain.award.service.distribute.impl;

import com.lyfx.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.lyfx.domain.award.model.entity.DistributeAwardEntity;
import com.lyfx.domain.award.model.entity.UserAwardRecordEntity;
import com.lyfx.domain.award.model.entity.UserCreditAwardEntity;
import com.lyfx.domain.award.model.vo.AwardStateVO;
import com.lyfx.domain.award.repository.IAwardRepository;
import com.lyfx.domain.award.service.distribute.IDistributeAward;
import com.lyfx.types.common.Constants;
import com.lyfx.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/5 19:35
 * @description 用户随机积分奖品，支持 award_config 透传，满足黑名单积分奖励
 */

@Component("user_credit_random")
public class UserCreditRandomAward implements IDistributeAward {
    
    @Resource
    private IAwardRepository awardRepository;
    
    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {
        Integer awardId = distributeAwardEntity.getAwardId();
        // 查询奖品配置 【优先走透传的随机积分奖品配置】
        // "1, 100"
        String awardConfig = distributeAwardEntity.getAwardConfig();
        if (StringUtils.isBlank(awardConfig)) {
            awardConfig = awardRepository.queryAwardConfig(awardId);
        }
        // ["1", "100"]
        String[] creditRange = awardConfig.split(Constants.SPLIT);
        if (creditRange.length != 2) {
            throw new RuntimeException("award_config: " + awardConfig + " 不是一个范围值，如 1,100");
        }
        
        // 生成随机积分
        BigDecimal creditAmount = generateRandom(new BigDecimal(creditRange[0]), new BigDecimal(creditRange[1]));
        
        // 构建聚合对象
        UserAwardRecordEntity userAwardRecordEntity = GiveOutPrizesAggregate.buildDistributeUserAwardRecordEntity(
                distributeAwardEntity.getUserId(),
                distributeAwardEntity.getOrderId(),
                distributeAwardEntity.getAwardId(),
                AwardStateVO.completed);
        
        UserCreditAwardEntity userCreditAwardEntity = GiveOutPrizesAggregate.buildUserCreditAwardEntity(distributeAwardEntity.getUserId(), creditAmount);
        
        GiveOutPrizesAggregate giveOutPrizesAggregate = new GiveOutPrizesAggregate();
        giveOutPrizesAggregate.setUserId(distributeAwardEntity.getUserId());
        giveOutPrizesAggregate.setUserAwardRecordEntity(userAwardRecordEntity);
        giveOutPrizesAggregate.setUserCreditAwardEntity(userCreditAwardEntity);
        
        // 存储发奖对象
        awardRepository.saveGiveOutPrizesAggregate(giveOutPrizesAggregate);
    }
    
    private BigDecimal generateRandom(BigDecimal min, BigDecimal max) {
        if (min.equals(max)) return min;
        BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.round(new MathContext(3));
    }
    
    
}
