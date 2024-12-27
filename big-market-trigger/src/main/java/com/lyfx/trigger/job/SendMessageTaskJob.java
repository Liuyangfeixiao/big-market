package com.lyfx.trigger.job;

import com.lyfx.domain.task.model.entity.TaskEntity;
import com.lyfx.domain.task.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.datanode.DataNode;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.apache.shardingsphere.infra.metadata.database.rule.RuleMetaData;
import org.apache.shardingsphere.mode.metadata.MetaDataContexts;
import org.apache.shardingsphere.sharding.rule.ShardingRule;
import org.apache.shardingsphere.sharding.rule.TableRule;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午6:28
 * @description 发送MQ消息 task 队列
 */

@Slf4j
@Component
public class SendMessageTaskJob {
    @Resource
    private ITaskService taskService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    
    
    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            // 逐个库扫描表
            List<TaskEntity> taskEntities = taskService.queryNoSendMessageTaskList();
            // 发送MQ消息
            for (TaskEntity taskEntity : taskEntities) {
                threadPoolExecutor.execute(() -> {
                    try {
                        taskService.sendMessage(taskEntity);
                        taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(), taskEntity.getMessageId());
                    } catch (Exception e) {
                        log.error("定时任务，发送MQ消息失败 userId: {} topic: {}", taskEntity.getUserId(), taskEntity.getTopic());
                        taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                    }
                });
            }
            log.info("测试结果: {}", taskEntities.size());
        } catch (Exception e) {
            log.error("定时任务，扫描MQ任务表发送消息失败", e);
        }
    }
}
