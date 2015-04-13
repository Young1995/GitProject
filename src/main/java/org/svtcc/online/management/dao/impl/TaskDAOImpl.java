package org.svtcc.online.management.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.TaskDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Task;

@Repository
public class TaskDAOImpl extends BaseDaoImpl<Task> implements TaskDAO {
	@SuppressWarnings("unchecked")
	@Override
	public PageSupport<Task> findTaskByCondition(Integer pageNo,
			Integer pageSize, String name, Date pubDate, Integer... status) {
		String hql = "select t from Task t where t.status in (:status)";
		if (StringUtils.isNotEmpty(name) || pubDate != null) {
			hql += " and (";

			if (StringUtils.isNotEmpty(name)) {
				hql += "t.name like :name";
			}

			if (pubDate != null && StringUtils.isNotEmpty(name)) {
				hql += " or t.pubDate = :pubDate";
			} else if (pubDate != null && StringUtils.isEmpty(name)) {
				hql += "t.pubDate = :pubDate";
			}

			hql += ")";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Query hql: " + hql);
		}

		String countQueryString = " select count(t) "
				+ removeSelect(removeOrders(hql));
		Query countQuery = getCurrentSession().createQuery(countQueryString);
		// 参数设置
		countQuery.setParameterList("status", status);
		if (StringUtils.isNotEmpty(name)) {
			countQuery.setParameter("name", "%" + name + "%");
		}

		if (pubDate != null) {
			countQuery.setParameter(":pubDate", pubDate);
		} 

		int totalCount = Integer.parseInt(countQuery.uniqueResult().toString());

		if (totalCount < 1) {
			return new PageSupport<Task>(pageSize);
		}
		// 实际查询返回分页对象
		int startIndex = PageSupport.getStartOfPage(pageNo, pageSize);
		Query query = getCurrentSession().createQuery(hql);

		query.setParameterList("status", status);
		if (StringUtils.isNotEmpty(name)) {
			query.setParameter("name", "%" + name + "%");
		}

		if (pubDate != null) {
			query.setParameter(":pubDate", pubDate);
		} 

		List<Task> list = query.setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
		logger.info(list.size());
		return new PageSupport<Task>(startIndex, totalCount, pageSize, list);
	}
}
