package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseAttendance;

public interface CourseAttendanceDAO extends BaseDAO<CourseAttendance> {
    public PageSupport<CourseAttendance> findAttendance(
            Integer pageNo, Integer pageSize, Integer teacherId, Integer checkRole);
    public PageSupport<CourseAttendance> findAttendance(
            Integer pageNo, Integer pageSize, Integer checkRole);
}
