package org.svtcc.online.management.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.HomeworkAnswerDAO;
import org.svtcc.online.management.dao.HomeworkDAO;
import org.svtcc.online.management.dao.HomeworkDetailDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.HomeworkAnswer;
import org.svtcc.online.management.domain.HomeworkDetail;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.HomeworkAnswerService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class HomeworkAnswerServiceImpl implements HomeworkAnswerService{

    @Autowired
    private HomeworkAnswerDAO homeworkAnswerDAO;
    
    @Autowired
    private HomeworkDAO homeworkDAO;
    
    @Autowired
    private HomeworkDetailDAO homeworkDetailDAO;
    
    @Override
    public void saveOrUpdate(HomeworkAnswer answer) {
        User user = UserUtil.getCurrentUser();
        if(answer.getId() == null || answer.getId() == 0) {
            answer.setId(null);
            answer.setCreateDate(new Date());
            answer.setCreateUser(user.getName());
        }
        
        HomeworkDetail detail = homeworkDetailDAO.find(answer.getQuestionId());
        answer.setQuestion(detail);
        
        answer.setUpdateDate(new Date());
        answer.setUpdateUser(user.getName());
        
        answer.setStudentId(user.getId());
        
        homeworkAnswerDAO.saveOrUpdate(answer);
    }

    @Override
    public void deleteById(Integer id) {
        homeworkAnswerDAO.deleteById(id);
    }

    @Override
    public PageSupport<HomeworkAnswer> findPagination(int pageNo, int pageSize) {
        return null;
    }
    
    /**
     * 根据课程Id，学生Id查询该课程的答案
     */
    @Override
    public List<HomeworkAnswer> findHomeworkAnswersByCourseId(Integer courseId) {
        User user = UserUtil.getCurrentUser();
        return homeworkAnswerDAO.findHomeworkAnswersByCourseId(courseId, user.getId());
    }

}
