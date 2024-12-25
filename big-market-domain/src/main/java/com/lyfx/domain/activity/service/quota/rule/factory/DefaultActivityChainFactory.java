package com.lyfx.domain.activity.service.quota.rule.factory;

import com.lyfx.domain.activity.service.quota.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/21 下午5:35
 * @description 活动责任链工厂-负责产生责任链
 */

@Service
public class DefaultActivityChainFactory {
    private final IActionChain actionChain;
    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.getCode());
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.getCode()));
    }
    
    public IActionChain openActionChain() {
        return this.actionChain;
    }
    
    @Getter
    @AllArgsConstructor
    public enum ActionModel {
        activity_base_action("activity_base_action", "活动时间、状态校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;
        
        private final String code;
        private final String info;
    }
}
