package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.OnlineCourseDAO;
import org.svtcc.online.management.domain.OnlineCourse;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
@Repository
public class OnlineCourseDAOImpl extends BaseDaoImpl<OnlineCourse> implements OnlineCourseDAO {
    @Override
    public List<OnlineCourse> findList(int pageNo, int pageSize, Integer userId) {
        String hql = "from OnlineCourse onlineCourse where onlineCourse.userId=" + userId;
        return pagedQuery(hql, pageNo, pageSize);
    }

    @Override
    public List<OnlineCourse> findList(int pageNo, int pageSize, Integer userId, String courseName) {
        StringBuffer hql = new StringBuffer("from OnlineCourse onlineCourse where onlineCourse.userId=" + userId);
        if (null != courseName && !courseName.trim().isEmpty()) {
            hql.append("and onlineCourse.name like '%" + courseName + "%'");
        }
        return pagedQuery(hql.toString(), pageNo, pageSize);
    }
}
