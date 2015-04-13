package org.svtcc.online.management.service;


import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.OnlineCourse;
import org.svtcc.online.management.dto.OnlineCourseDTO;

import java.util.List;

/**
 * Created  on 1/15/15.
 */
public interface OnlineCourseService extends BaseService<OnlineCourseDTO> {
    OnlineCourseDTO findById(Integer courseId);

    PageSupport<OnlineCourseDTO> findPagination(int pageNo, int pageSize,int userId,String name, Integer status, List<Integer> departments,
                                                List<Integer> majors);

    void updateStatus(Integer id,OnlineCourse.STATUS status);

    void updateFeature(Integer id,String feature);

    void updateScheme(Integer id,String scheme);

    void updateReference(Integer id,String reference);

    void updatePoint(Integer id,String point);

}
