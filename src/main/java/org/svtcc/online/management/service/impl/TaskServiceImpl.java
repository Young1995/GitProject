package org.svtcc.online.management.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.DepartmentDAO;
import org.svtcc.online.management.dao.MajorDAO;
import org.svtcc.online.management.dao.TaskDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.domain.Task;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.TaskService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private MajorDAO majorDAO;

    @Override
    public void saveOrUpdate(Task task) {
        if (task != null) {
            User user = UserUtil.getCurrentUser();
            if (task.getId() == null || task.getId() == 0) {
                task.setId(null);
                // 添加
                task.setCreateDate(new Date());
                task.setCreateUser(user.getUsername());
                task.setPubUser(user.getName());
            }

            task.setUpdateDate(new Date());
            task.setUpdateUser(user.getUsername());

            // department
            Department department = departmentDAO.findDepartmentById(task
                    .getDepId());
            Major major = majorDAO.find(task.getMajorId());

            task.setDepartment(department);
            task.setMajor(major);

            taskDAO.saveOrUpdate(task);
        }
    }

    @Override
    public void deleteById(Integer id) {
        taskDAO.deleteById(id);

    }

    @Override
    public PageSupport<Task> findPagination(int pageNo, int pageSize) {
        return taskDAO.findPagination(pageNo, pageSize);
    }

    /**
     * 查询 1. status（必须） 2. 名称（可选） 3. 发布时间（可选）
     * 
     */
    @Override
    public PageSupport<Task> findTaskByCondition(Integer pageNo,
            Integer pageSize, String name, Date pubDate, Integer... status) {
        Integer defaultStatus = 1;
        if (pageNo == null) {
            pageNo = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        if (status != null && status.length > 0) {
            return taskDAO.findTaskByCondition(pageNo, pageSize, name, pubDate,
                    status);
        } else {
            return taskDAO.findTaskByCondition(pageNo, pageSize, name, pubDate,
                    defaultStatus);
        }

    }

    @Override
    public Task findTaskById(Integer id) {
        return taskDAO.find(id);
    }
    
    @Override
    public boolean executeTask(Integer id, String executeFeedback, String executeFileName, String executeFileURL) {
        Task task = findTaskById(id);
        if (task != null) {
            if (StringUtils.isNotEmpty(executeFeedback)) {
                task.setExecuteFeedback(executeFeedback);
                task.setStatus(2);
                task.setExecuteFileName(executeFileName);
                task.setExecuteFileURL(executeFileURL);;
                taskDAO.saveOrUpdate(task);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean examTask(Integer id, String examFeedback, Integer score, String examFileName, String examFileURL) {
        Task task = findTaskById(id);
        if (task != null) {
            if (StringUtils.isNotEmpty(examFeedback)) {
                task.setExamFeedback(examFeedback);
                task.setExamScore(score);
                task.setStatus(3);
                task.setExamFileName(examFileName);
                task.setExamFileURL(examFileURL);
                taskDAO.saveOrUpdate(task);
                return true;
            }
            return true;
        }
        return false;
    }

}
