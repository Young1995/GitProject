package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseKeyPoint;

public interface CourseKeyPointService extends BaseService<CourseKeyPoint> {
	public CourseKeyPoint findCourseKeyPointById(Integer id);
	
	public PageSupport<CourseKeyPoint> findPagination4CurrentUser(Integer pageNo, Integer pageSize);
}
