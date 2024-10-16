package com.lyfx.domain.strategy.service.raffle;

import com.lyfx.domain.strategy.model.entity.RaffleFactorEntity;
import com.lyfx.domain.strategy.model.entity.RuleActionEntity;
import com.lyfx.domain.strategy.model.entity.RuleMatterEntity;
import com.lyfx.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lyfx.domain.strategy.repository.IStrategyRepository;
import com.lyfx.domain.strategy.service.AbstractRaffleStrategy;
import com.lyfx.domain.strategy.service.armory.IStrategyDispatch;
import com.lyfx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.lyfx.domain.strategy.service.rule.filter.ILogicFilter;
import com.lyfx.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yangfeixaio Liu
 * @time 2024/6/15 下午5:52
 * @description
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {
    @Resource
    private DefaultLogicFactory logicFactory;
    
    
    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch,
                                 DefaultChainFactory defaultChainFactory) {
        super(repository, strategyDispatch, defaultChainFactory);
    }
    
    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (logics == null || logics.length == 0) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 1. 获取规则引擎
        Map<String, ILogicFilter<RuleActionEntity.RaffleBeforeEntity>> logicFilterGroup = logicFactory.openLogicFilter();
        
        // 2. 黑名单规则优先过滤
        String ruleBlackList = Arrays.stream(logics)
                .filter(str -> str.contains(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode()))
                .findFirst()
                .orElse(null);
        
        if (StringUtils.isNoneBlank(ruleBlackList)) {
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> logicFilter = logicFilterGroup.get(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(ruleMatterEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) {
                return ruleActionEntity;  // 接管
            }
        }
        
        // 顺序过滤剩余规则
        List<String> ruleList = Arrays.stream(logics)
                .filter(s -> !s.equals(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode()))
                .collect(Collectors.toList());
        
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = null;
        
        for (String ruleModel : ruleList) { // rule_weight
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> logicFilter = logicFilterGroup.get(ruleModel);
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(ruleMatterEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(ruleModel);
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            log.info("抽奖前规则过滤 userId: {} ruleModel: {} code: {} info: {}", raffleFactorEntity.getUserId(), ruleModel,
                    ruleActionEntity.getCode(), ruleActionEntity.getInfo());
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) {return ruleActionEntity;}
        }
        
        return ruleActionEntity;
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleDuringEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (logics == null || logics.length == 0) {
            return RuleActionEntity.<RuleActionEntity.RaffleDuringEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        Map<String, ILogicFilter<RuleActionEntity.RaffleDuringEntity>> logicFilterGroup = logicFactory.openLogicFilter();

        RuleActionEntity<RuleActionEntity.RaffleDuringEntity> ruleActionEntity = null;
        for (String ruleModel : logics) {
            ILogicFilter<RuleActionEntity.RaffleDuringEntity> logicFilter = logicFilterGroup.get(ruleModel);
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(raffleFactorEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(ruleModel);
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            // 非放行结果 拦截
            log.info("抽奖中规则过滤 userId: {} ruleModel: {} code: {} info: {}", raffleFactorEntity.getUserId(), ruleModel,
                    ruleActionEntity.getCode(), ruleActionEntity.getInfo());
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) {
                return ruleActionEntity;
            }
        }
        return ruleActionEntity;
    }
}
