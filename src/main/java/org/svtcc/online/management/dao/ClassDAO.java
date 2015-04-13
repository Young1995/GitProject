package org.svtcc.online.management.dao;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Clazz;

/**
 * Created  on 1/11/15.
 */
public interface ClassDAO extends BaseDAO<Clazz> {
    PageSupport<Clazz> findByMajor(int pageNo, int pageSize, String major);
}
