package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseKeyPoint;

public interface CourseKeyPointDAO extends BaseDAO<CourseKeyPoint> {
	public PageSupport<CourseKeyPoint> findPaginationByUserId(Integer pageNo,
			Integer pageSize, Integer userId);
}
