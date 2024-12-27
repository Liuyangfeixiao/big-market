package com.lyfx.domain.task.service;

import com.lyfx.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午6:37
 * @description 消息任务服务接口
 */
public interface ITaskService {
    List<TaskEntity> queryNoSendMessageTaskList();
    
    void sendMessage(TaskEntity taskEntity);
    
    void updateTaskSendMessageCompleted(String userId, String messageId);
    
    void updateTaskSendMessageFail(String userId, String messageId);
}
