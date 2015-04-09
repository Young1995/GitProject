package org.svtcc.online.management.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.MajorDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Major;

/**
 * Created  on 1/11/15.
 */
@Repository
public class MajorDAOImpl extends BaseDaoImpl<Major> implements MajorDAO {
    @Override
    public PageSupport<Major> findByDepartmentName(int pageNo, int pageSize, String departmentName) {
        if (StringUtils.isEmpty(departmentName)) {
            return findPagination("from Major", pageNo, pageSize);
        }
        return findPagination("from Major major where major.department.name=?", pageNo, pageSize, departmentName);

    }
}
