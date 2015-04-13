package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Homework;

public interface HomeworkDAO extends BaseDAO<Homework> {
	public PageSupport<Homework> findPaginationByUserId(Integer pageNo,
			Integer pageSize, Integer userId);
	public PageSupport<Homework> findHomeworkForStudent(Integer studentId,
			Integer pageNo, Integer pageSize);
}
