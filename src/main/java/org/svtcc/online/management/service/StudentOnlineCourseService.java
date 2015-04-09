package org.svtcc.online.management.service;


import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.StudentOnlineCourse;
import org.svtcc.online.management.dto.StudentOnlineCourseDTO;

import java.util.List;

/**
 * Created by hanrenwei on 3/17/15.
 */
public interface StudentOnlineCourseService extends BaseService<StudentOnlineCourseDTO>  {
    PageSupport<StudentOnlineCourseDTO> findPagination(int pageNo, int pageSize,int userId,String name, Integer status, List<Integer> departments,
                                                       List<Integer> majors);

    void updateStatus(Integer courseId,StudentOnlineCourse.Status status);

    void selectCourse(Integer courseId,StudentOnlineCourse.Status status);


    StudentOnlineCourseDTO findById(Integer id);

    PageSupport<StudentOnlineCourseDTO>  findPagination(int pageNo, int pageSize, int userId, String name, List<Integer> departments, List<Integer> majors);

}
