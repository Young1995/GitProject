package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Clazz;

/**
 * Created  on 1/15/15.
 */
public interface ClassService extends BaseService<Clazz> {
    PageSupport<Clazz> findByMajor(int pageNo, int pageSize, String major);
}
