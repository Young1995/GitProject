package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.dto.OnlineCoursePrincipalDTO;

/**
 * Created by hanrenwei on 3/3/15.
 */
public interface OnlineCoursePrincipalService extends BaseService<OnlineCoursePrincipalDTO> {
    PageSupport<OnlineCoursePrincipalDTO> findPagination(int pageNo, int pageSize, int courseId);

    OnlineCoursePrincipalDTO findById(Integer principalId);
}
