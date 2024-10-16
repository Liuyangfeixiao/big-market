package com.lyfx.domain.strategy.service;

import com.lyfx.domain.strategy.model.entity.RaffleAwardEntity;
import com.lyfx.domain.strategy.model.entity.RaffleFactorEntity;
import com.lyfx.domain.strategy.model.entity.RuleActionEntity;
import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import com.lyfx.domain.strategy.service.rule.chain.ILogicChain;
import com.lyfx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Yangfeixaio Liu
 * @time 2024/5/7 下午12:07
 * @description 抽奖策略抽象类
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    // 策略仓储服务 -> domain层像一个大厨, repository 提供米面粮油
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;
    //  抽奖的责任链 -> 从抽奖的规则中，解耦出前置规则为责任链处理
    private final DefaultChainFactory defaultChainFactory;
    
    
    // 通过构造函数进行注入
    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch,
                                  DefaultChainFactory defaultChainFactory) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
    }
    
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        
        // 2. 获取前置规则的抽奖责任链
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        
        // 3. 通过前置规则的责任链获得 awardId
        Integer awardId = logicChain.logic(userId, strategyId);

        // 4. 查询奖品规则「抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）」
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId, awardId);

        RuleActionEntity<RuleActionEntity.RaffleDuringEntity> ruleActionDuringEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                .userId(userId)
                .strategyId(strategyId)
                .awardId(awardId)
                .build(), strategyAwardRuleModelVO.raffleCenterRuleModelList());

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionDuringEntity.getCode())) {
            log.info("【临时日志】抽奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励");
            return RaffleAwardEntity.builder()
                    .awardDesc("抽奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励")
                    .build();
        }

        return RaffleAwardEntity.builder()
                .awardId(awardId).build();
    }
    
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
    protected abstract RuleActionEntity<RuleActionEntity.RaffleDuringEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}
