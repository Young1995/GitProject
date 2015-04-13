package org.svtcc.online.management.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.HomeworkDetailDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.HomeworkDetail;


@Repository
public class HomeworkDetailDAOImpl extends BaseDaoImpl<HomeworkDetail>
        implements HomeworkDetailDAO {
    @Override
    public PageSupport<HomeworkDetail> findByUserIdAndHomeworkId(Integer homeworkId, Integer userId, Integer pageNo, Integer pageSize) {
        return findPagination("from HomeworkDetail h where h.belongUserId = ? and h.homework.id = ?", pageNo, pageSize, userId, homeworkId);
    }

	@Override
	public List<HomeworkDetail> findDetailByHomeworkId(Integer homeworkId) {
		List<HomeworkDetail> result = find("from HomeworkDetail h where h.homework.id = ?", homeworkId);
		return result;
	}
}
