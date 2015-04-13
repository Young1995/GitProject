package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Homework;

public interface HomeworkService extends BaseService<Homework> {
	public Homework findById(Integer id);

	public PageSupport<Homework> findPagination4CurrentUser(Integer pageNo,
			Integer pageSize);
	
	public boolean pubHomework(Integer homeworkId);
	
	public PageSupport<Homework> findHomeworksForStudent(Integer pageNo,
			Integer pageSize);
}
