package com.lyfx.trigger.job;

import com.lyfx.domain.activity.model.vo.ActivitySkuStockKeyVO;
import com.lyfx.domain.activity.service.ISkuStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/22 下午8:33
 * @description 更新活动sku库存任务
 */
@Slf4j
@Component
public class UpdateActivitySkuStockJob {
    @Resource
    private ISkuStock skuStock;
    @Resource
    private ThreadPoolExecutor executor;
    
    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            List<Long> skus = skuStock.querySkuList();
            for (Long sku : skus) {
                executor.execute(() -> {
                    ActivitySkuStockKeyVO activitySkuStockKeyVO = null;
                    try {
                        activitySkuStockKeyVO = skuStock.takeQueueValue(sku);
                    } catch (InterruptedException e) {
                        log.error("定时任务，更新活动sku库存失败 sku: {}", sku);
                    }
                    if (activitySkuStockKeyVO == null) {return;}
                    log.info("定时任务，更新活动sku库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                    skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
                });
            }
        } catch (Exception e) {
            log.error("定时任务，更新活动sku库存失败", e);
        }
    }
}
