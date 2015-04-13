package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.QuestionType;

public interface QuestionTypeService extends BaseService<QuestionType>{
	public PageSupport<QuestionType> findPaginationByType(Integer pageNo, Integer pageSize, Integer type);
}
