package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseAttendance;

public interface CourseAttendanceService extends BaseService<CourseAttendance> {
	public void generateAttendanceRecordForCheck();
	public PageSupport<CourseAttendance> findCourseAttendance(
            Integer pageNo, Integer pageSize, Integer checkRole);
	public CourseAttendance findCourseAttendanceById(Integer id);
	
	public boolean checkAttendance(CourseAttendance attend);
	public PageSupport<CourseAttendance> findCourseAttendanceStatistic(
			Integer pageNo, Integer pageSize);
}
