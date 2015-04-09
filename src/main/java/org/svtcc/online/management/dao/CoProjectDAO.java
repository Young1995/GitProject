package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CoProject;

public interface CoProjectDAO extends BaseDAO<CoProject>{
	public PageSupport<CoProject> findCoProjectByStatus(Integer pageNo,
			Integer pageSize, Integer... status);
}
