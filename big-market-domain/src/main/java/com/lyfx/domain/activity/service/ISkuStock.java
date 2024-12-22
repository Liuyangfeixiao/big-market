package com.lyfx.domain.activity.service;

import com.lyfx.domain.activity.model.vo.ActivitySkuStockKeyVO;
import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/22 下午7:37
 * @description 活动sku库存处理接口
 */
public interface ISkuStock {
    
    /**
     * 获取活动sku库存消耗队列
     * @param sku
     * @return 活动sku库存key信息
     * @throws InterruptedException
     */
    ActivitySkuStockKeyVO takeQueueValue(Long sku) throws InterruptedException;
    
    /**
     * 清空队列
     * @param sku
     */
    void clearQueueValue(Long sku);
    
    void updateActivitySkuStock(Long sku);
    
    void clearActivitySkuStock(Long sku);
    
    List<Long> querySkuList();
}
