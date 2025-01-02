package com.lyfx.domain.task.service;

import com.lyfx.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午6:37
 * @description 消息任务服务接口
 */
public interface ITaskService {
    /**
     * 查询发送MQ失败和超时一分钟未发送的消息任务
     * @return 未发送的消息任务10条
     */
    List<TaskEntity> queryNoSendMessageTaskList();
    
    void sendMessage(TaskEntity taskEntity);
    
    void updateTaskSendMessageCompleted(String userId, String messageId);
    
    void updateTaskSendMessageFail(String userId, String messageId);
}
