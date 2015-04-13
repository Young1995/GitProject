package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.CourseKeyPointDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseKeyPoint;

@Repository
public class CourseKeyPointDAOImpl extends BaseDaoImpl<CourseKeyPoint>
		implements CourseKeyPointDAO {

	@Override
	public PageSupport<CourseKeyPoint> findPaginationByUserId(Integer pageNo,
			Integer pageSize, Integer userId) {
		return findPagination("from CourseKeyPoint t where t.belongUserId = ?",
				pageNo, pageSize, userId);
	}

}
