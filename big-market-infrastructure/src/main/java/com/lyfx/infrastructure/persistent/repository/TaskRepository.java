package com.lyfx.infrastructure.persistent.repository;

import com.lyfx.domain.task.model.entity.TaskEntity;
import com.lyfx.domain.task.repository.ITaskRepository;
import com.lyfx.infrastructure.event.EventPublisher;
import com.lyfx.infrastructure.persistent.dao.ITaskDao;
import com.lyfx.infrastructure.persistent.po.Task;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/26 下午8:20
 * @description 任务服务仓储实现
 */

@Repository
public class TaskRepository implements ITaskRepository {
    @Resource
    private ITaskDao taskDao;
    @Resource
    private EventPublisher eventPublisher;
    
    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        List<Task> tasks = taskDao.queryNoSendMessageTaskList();
        List<TaskEntity> taskEntities = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(task.getUserId());
            taskEntity.setTopic(task.getTopic());
            taskEntity.setMessage(task.getMessage());
            taskEntity.setMessageId(task.getMessageId());
            taskEntities.add(taskEntity);
        }
        return taskEntities;
    }
    
    @Override
    public void sendMessage(TaskEntity taskEntity) {
        eventPublisher.publish(taskEntity.getUserId(), taskEntity.getMessage());
    }
    
    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        Task taskReq = new Task();
        taskReq.setUserId(userId);
        taskReq.setMessageId(messageId);
        taskDao.updateTaskSendMessageCompleted(taskReq);
    }
    
    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        Task taskReq = new Task();
        taskReq.setUserId(userId);
        taskReq.setMessageId(messageId);
        taskDao.updateTaskSendMessageFail(taskReq);
    }
}
