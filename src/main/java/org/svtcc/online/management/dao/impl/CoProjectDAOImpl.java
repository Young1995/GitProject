package org.svtcc.online.management.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.CoProjectDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CoProject;

@Repository
public class CoProjectDAOImpl extends BaseDaoImpl<CoProject> implements
		CoProjectDAO {
	@SuppressWarnings("unchecked")
	public PageSupport<CoProject> findCoProjectByStatus(Integer pageNo,
			Integer pageSize, Integer... status) {
		String hql = "select p from CoProject p where p.status in (:status)";
		
		// Count查询
        String countQueryString = " select count (p) " + removeSelect(removeOrders(hql));
        Query countQuery = getCurrentSession().createQuery(countQueryString);
        countQuery.setParameterList("status", status);
        int totalCount = Integer.parseInt(countQuery.uniqueResult().toString());

        if (totalCount < 1) {
            return new PageSupport<CoProject>(pageSize);
        }
        // 实际查询返回分页对象
        int startIndex = PageSupport.getStartOfPage(pageNo, pageSize);
        Query query = getCurrentSession().createQuery(hql);
        query.setParameterList("status", status);
		List<CoProject> list = query.setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
        logger.info(list.size());
        return new PageSupport<CoProject>(startIndex, totalCount, pageSize, list);
	}
}
