package org.svtcc.online.management.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.HomeworkDAO;
import org.svtcc.online.management.dao.HomeworkDetailDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.HomeworkDetail;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.HomeworkDetailService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class HomeworkDetailServiceImpl implements HomeworkDetailService {

    @Autowired
    private HomeworkDetailDAO homeworkDetailDAO;

    @Autowired
    private HomeworkDAO homeworkDAO;

    @Override
    public void saveOrUpdate(HomeworkDetail detail) {
        User user = UserUtil.getCurrentUser();

        if (detail.getId() == null || detail.getId() == 0) {
            detail.setId(null);
            detail.setCreateDate(new Date());
            detail.setCreateUser(user.getName());
        }

        detail.setUpdateDate(new Date());
        detail.setUpdateUser(user.getName());

        // 设置作业对象
        detail.setHomework(homeworkDAO.find(detail.getHomeworkId()));

        detail.setBelongUserId(user.getId());

        homeworkDetailDAO.saveOrUpdate(detail);
    }

    @Override
    public void deleteById(Integer id) {
        homeworkDetailDAO.deleteById(id);
    }

    @Override
    public PageSupport<HomeworkDetail> findPagination(int pageNo, int pageSize) {
        return homeworkDetailDAO.findPagination(pageNo, pageSize);
    }

    @Override
    public HomeworkDetail findById(Integer id) {
        return homeworkDetailDAO.find(id);
    }

    @Override
    public PageSupport<HomeworkDetail> findPagination4CurrentUserByHomeworkId(
            Integer homeworkId, Integer pageNo, Integer pageSize) {
        User user = UserUtil.getCurrentUser();

        if (pageNo == null || pageNo == 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        return homeworkDetailDAO.findByUserIdAndHomeworkId(homeworkId,
                user.getId(), pageNo, pageSize);

    }

	@Override
	public List<HomeworkDetail> findByHomeworkId(Integer homeworkId) {
		return homeworkDetailDAO.findDetailByHomeworkId(homeworkId);
	}
}
