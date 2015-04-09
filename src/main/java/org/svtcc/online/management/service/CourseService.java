package org.svtcc.online.management.service;

import org.svtcc.online.management.dto.CourseDTO;

/**
 * Created  on 1/15/15.
 */
public interface CourseService extends BaseService<CourseDTO> {
    CourseDTO findById(Integer courseId);
}
