package org.svtcc.online.management.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.ClassDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Clazz;

/**
 * Created  on 1/11/15.
 */
@Repository
public class ClassDAOImpl extends BaseDaoImpl<Clazz> implements ClassDAO {
    @Override
    public PageSupport<Clazz> findByMajor(int pageNo, int pageSize, String majorName) {

        if (!StringUtils.isEmpty(majorName) && !StringUtils.isEmpty(majorName)) {
            return findPagination("from Clazz clazz where clazz.major.name=?", pageNo, pageSize, majorName);
        } else {
            return findPagination("from Clazz", pageNo, pageSize);
        }

    }
}
