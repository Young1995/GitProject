package org.svtcc.online.management.service;

import java.util.List;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.HomeworkDetail;

public interface HomeworkDetailService extends BaseService<HomeworkDetail> {
    public HomeworkDetail findById(Integer id);
    public PageSupport<HomeworkDetail> findPagination4CurrentUserByHomeworkId(
            Integer homeworkId, Integer pageNo, Integer pageSize);
    public List<HomeworkDetail> findByHomeworkId(Integer homeworkId);
}
