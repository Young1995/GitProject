package org.svtcc.online.management.dao;

import java.util.Date;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Task;

public interface TaskDAO extends BaseDAO<Task> {
	public PageSupport<Task> findTaskByCondition(Integer pageNo,
			Integer pageSize, String name, Date pubDate, Integer... status);
}
