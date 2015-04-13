package org.svtcc.online.management.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.CourseAttendanceDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseAttendance;

@Repository
public class CourseAttendanceDAOImpl extends BaseDaoImpl<CourseAttendance>
		implements CourseAttendanceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public PageSupport<CourseAttendance> findAttendance(Integer pageNo,
			Integer pageSize, Integer ueserId, Integer checkRole) {

		String hql = "select c from CourseAttendance c where c.course.teacherId = :userId";

		if (checkRole == 2) {
			hql = "select c from CourseAttendance c where c.course.studentId = :userId";
		} else if (checkRole == 3) {
			hql = "select c from CourseAttendance c where c.studentId = :userId";
		}

		hql += " and c.checkRole = :checkRole";

		// Count查询
		String countQueryString = "select count (c) "
				+ removeSelect(removeOrders(hql));
		Query countQuery = getCurrentSession().createQuery(countQueryString);
		countQuery.setParameter("userId", ueserId);
		countQuery.setParameter("checkRole", checkRole);
		int totalCount = Integer.parseInt(countQuery.uniqueResult().toString());

		if (totalCount < 1) {
			return new PageSupport<CourseAttendance>(pageSize);
		}
		// 实际查询返回分页对象
		int startIndex = PageSupport.getStartOfPage(pageNo, pageSize);
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("userId", ueserId);
		query.setParameter("checkRole", checkRole);
		List<CourseAttendance> list = query.setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
		return new PageSupport<CourseAttendance>(startIndex, totalCount,
				pageSize, list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageSupport<CourseAttendance> findAttendance(Integer pageNo,
			Integer pageSize, Integer checkRole) {
		String hql = "select c from CourseAttendance c where c.checkRole = :checkRole";

		// Count查询
		String countQueryString = "select count (c) "
				+ removeSelect(removeOrders(hql));
		Query countQuery = getCurrentSession().createQuery(countQueryString);
		countQuery.setParameter("checkRole", checkRole);
		int totalCount = Integer.parseInt(countQuery.uniqueResult().toString());

		if (totalCount < 1) {
			return new PageSupport<CourseAttendance>(pageSize);
		}
		// 实际查询返回分页对象
		int startIndex = PageSupport.getStartOfPage(pageNo, pageSize);
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("checkRole", checkRole);
		List<CourseAttendance> list = query.setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
		return new PageSupport<CourseAttendance>(startIndex, totalCount,
				pageSize, list);
	}

}
