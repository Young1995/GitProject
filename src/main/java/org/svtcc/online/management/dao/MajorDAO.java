package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Major;

/**
 * Created  on 1/11/15.
 */
public interface MajorDAO extends BaseDAO<Major>{
    PageSupport<Major> findByDepartmentName(int pageNo, int pageSize, String departmentName);
}
