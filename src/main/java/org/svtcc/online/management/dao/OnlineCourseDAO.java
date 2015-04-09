package org.svtcc.online.management.dao;

import org.svtcc.online.management.domain.OnlineCourse;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
public interface OnlineCourseDAO extends BaseDAO<OnlineCourse> {

    List<OnlineCourse> findList(int pageNo, int pageSize,Integer userId);

    List<OnlineCourse> findList(int pageNo, int pageSize,Integer userId,String curseName);
}
