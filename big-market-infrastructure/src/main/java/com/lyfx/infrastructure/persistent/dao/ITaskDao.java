package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午6:03
 * @description 任务表-发送MQ消息
 */

@Mapper
public interface ITaskDao {
    void insert(Task task);
    
    List<Task> queryNoSendMessageTaskList();
    
    void updateTaskSendMessageCompleted(Task task);
    
    void updateTaskSendMessageFail(Task task);
}
