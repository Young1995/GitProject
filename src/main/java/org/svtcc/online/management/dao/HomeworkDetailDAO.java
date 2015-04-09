package org.svtcc.online.management.dao;

import java.util.List;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.HomeworkDetail;

public interface HomeworkDetailDAO extends BaseDAO<HomeworkDetail> {
    public PageSupport<HomeworkDetail> findByUserIdAndHomeworkId(Integer homeworkId, Integer userId, Integer pageNo, Integer pageSize);
    public List<HomeworkDetail> findDetailByHomeworkId(Integer homeworkId);
}
