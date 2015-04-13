package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.domain.OnlineCourse;
import org.svtcc.online.management.dto.OnlineCourseDTO;

import static com.google.common.collect.Lists.transform;

/**
 * Created by hanrenwei on 3/17/15.
 */
public class ServiceUtils {
    public static OnlineCourseDTO transformToOnlineCourseDTO(OnlineCourse course) {
        OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
        onlineCourseDTO.setId(course.getId());
        onlineCourseDTO.setName(course.getName());
        onlineCourseDTO.setDepartmentNameList(transform(Lists.newArrayList(course.getDepartmentList()), new Function<Department, String>() {
            @Override
            public String apply(Department department) {
                return department.getName();
            }
        }));
        onlineCourseDTO.setTeacherNameList(transform(Lists.newArrayList(course.getDepartmentList()), new Function<Department, String>() {
            @Override
            public String apply(Department department) {
                return null != department.getDean() ? department.getDean().getName() : "";
            }
        }));

        onlineCourseDTO.setMajorNameList(transform(Lists.newArrayList(course.getMajorList()), new Function<Major, String>() {
            @Override
            public String apply(Major major) {
                return major.getName();
            }
        }));
        onlineCourseDTO.setStatus(course.getStatus().value());
        String statusName = "未通过";
        if (course.getStatus().equals(OnlineCourse.STATUS.UNAPPROVED)) {
            statusName = "未通过";
        } else if (course.getStatus().equals(OnlineCourse.STATUS.PROCESSING)) {
            statusName = "申报中";
        } else if (course.getStatus().equals(OnlineCourse.STATUS.UNPUBLISHED)) {
            statusName = "未发布";
        } else if (course.getStatus().equals(OnlineCourse.STATUS.PUBLISHED)) {
            statusName = "已发布";
        }
        onlineCourseDTO.setStatusName(statusName);
        onlineCourseDTO.setRejectReason(course.getDescription());
        return onlineCourseDTO;
    }
}
