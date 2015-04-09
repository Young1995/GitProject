package org.svtcc.online.management.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.QuestionTypeDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.QuestionType;

@Repository
public class QuestionTypeDAOImpl extends BaseDaoImpl<QuestionType> implements QuestionTypeDAO{

	@SuppressWarnings("unchecked")
	@Override
	public PageSupport<QuestionType> findPaginationByType(Integer pageNo,
			Integer pageSize, Integer type) {
		String hql = "select qt from QuestionType qt where qt.type = :type";
		
		// Count查询
        String countQueryString = " select count(qt) " + removeSelect(removeOrders(hql));
        Query countQuery = getCurrentSession().createQuery(countQueryString);
        countQuery.setParameter("type", type);
        int totalCount = Integer.parseInt(countQuery.uniqueResult().toString());

        if (totalCount < 1) {
            return new PageSupport<QuestionType>(pageSize);
        }
        // 实际查询返回分页对象
        int startIndex = PageSupport.getStartOfPage(pageNo, pageSize);
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("type", type);
		List<QuestionType> list = query.setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
        logger.info(list.size());
        return new PageSupport<QuestionType>(startIndex, totalCount, pageSize, list);
	}

}
