package com.lyfx.domain.task.service;

import com.lyfx.domain.task.model.entity.TaskEntity;
import com.lyfx.domain.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午7:54
 * @description 任务服务接口实现类
 */

@Service
public class TaskService implements ITaskService {
    
    @Resource
    private ITaskRepository taskRepository;
    
    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        return taskRepository.queryNoSendMessageTaskList();
    }
    
    @Override
    public void sendMessage(TaskEntity taskEntity) {
        taskRepository.sendMessage(taskEntity);
    }
    
    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        taskRepository.updateTaskSendMessageCompleted(userId, messageId);
    }
    
    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        taskRepository.updateTaskSendMessageFail(userId, messageId);
    }
}
