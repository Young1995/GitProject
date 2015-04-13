package org.svtcc.online.management.dao.impl;

import java.util.List;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.HomeworkDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Homework;

@Repository
public class HomeworkDAOImpl extends BaseDaoImpl<Homework> implements
		HomeworkDAO {
	public PageSupport<Homework> findPaginationByUserId(Integer pageNo,
			Integer pageSize, Integer userId) {
		return findPagination("from Homework t where t.belongUserId = ?",
				pageNo, pageSize, userId);
	}

	@SuppressWarnings("unchecked")
	public PageSupport<Homework> findHomeworkForStudent(Integer studentId,
			Integer pageNo, Integer pageSize) {
		String sql = "select t.* from svtcc_homework as t left join svtcc_student_online_course as c on t.online_course_id = c.online_course_id where c.user_id = ? and c.status = 4";
		
		 // Count查询
        String countQueryString = " select count(*) " + removeSelect(removeOrders(sql));
        Query query = getCurrentSession().createSQLQuery(countQueryString);
        query.setParameter(0, studentId);
        Object result = query.uniqueResult();
        int totalCount = (result == null ? 0 : Integer.parseInt(result.toString()));

        if (totalCount < 1) {
            return new PageSupport<Homework>(pageSize);
        }
        // 实际查询返回分页对象
        int startIndex = PageSupport.getStartOfPage(pageNo, pageSize);
        Query listQuery = getCurrentSession().createSQLQuery(sql).addEntity(Homework.class);
        listQuery.setParameter(0, studentId);
		List<Homework> list = listQuery.setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
        logger.info(list.size());
        return new PageSupport<Homework>(startIndex, totalCount, pageSize, list);
	}
}
