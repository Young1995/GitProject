package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Major;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
public interface MajorService extends BaseService<Major>{
    List<Major> findAll();
    PageSupport<Major> findByDepartment(int pageNo, int pageSize,String departmentName);
}
