package org.svtcc.online.management.service;

import java.util.Date;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Task;

public interface TaskService extends BaseService<Task> {
    public PageSupport<Task> findTaskByCondition(Integer pageNo,
            Integer pageSize, String name, Date pubDate, Integer... status);

    public Task findTaskById(Integer id);

    public boolean executeTask(Integer id, String executeFeedback, String executeFileName, String executeFileURL);
    
    public boolean examTask(Integer id, String examFeedback, Integer score, String examFileName, String examFileURL);
}
