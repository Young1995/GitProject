package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.QuestionType;

public interface QuestionTypeDAO extends BaseDAO<QuestionType>{
	public PageSupport<QuestionType> findPaginationByType(Integer pageNo, Integer pageSize, Integer type);
}
