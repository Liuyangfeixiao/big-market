package com.lyfx.domain.task.repository;

import com.lyfx.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午7:51
 * @description 任务服务仓储接口
 */

public interface ITaskRepository {
    
    List<TaskEntity> queryNoSendMessageTaskList();
    
    void sendMessage(TaskEntity taskEntity);
    
    void updateTaskSendMessageCompleted(String userId, String messageId);
    
    void updateTaskSendMessageFail(String userId, String messageId);
}
