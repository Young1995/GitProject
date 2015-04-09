package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.OnlineCourseDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.domain.OnlineCourse;
import org.svtcc.online.management.dto.OnlineCourseDTO;
import org.svtcc.online.management.service.OnlineCourseService;
import org.svtcc.online.management.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created  on 1/11/15.
 */
@Service
public class OnlineCourseServiceImpl implements OnlineCourseService {
    @Autowired
    private OnlineCourseDAO courseDAO;

    @Override
    public void saveOrUpdate(OnlineCourseDTO courseDTO) {
        OnlineCourse course = new OnlineCourse();
        course.setUserId(UserUtil.getCurrentUser().getId());
        if (null != courseDTO.getId()) {
            course = courseDAO.find(courseDTO.getId());
            if (null != courseDTO.getStatus()) {
                course.setStatus(OnlineCourse.STATUS.valueOf(courseDTO.getStatus()));
            }

        } else {
            course.setStatus(OnlineCourse.STATUS.UNAPPROVED);
        }

        course.setDescription(courseDTO.getDescription());
        course.setName(courseDTO.getName());
        course.setCourseUrl(courseDTO.getCourseUrl());
        course.setDeclareUrl(courseDTO.getDeclareUrl());
        course.setEnableAutoApprove(courseDTO.getEnableAutoApprove());
        course.setEnablePublic(courseDTO.getEnablePublic());
        course.setEnableShowInFront(courseDTO.getEnableShowInFront());
        course.setEnableCourseUrl(courseDTO.getEnableCourseUrl());
        course.setEnableDeclareUrl(courseDTO.getEnableDeclareUrl());
        if (null != courseDTO.getMajorList()) {
            List<Major> majorList = new ArrayList<Major>();
            for (Integer id : courseDTO.getMajorList()) {
                Major major = new Major();
                major.setId(id);
                majorList.add(major);
            }
            course.setMajorList(Sets.newHashSet(majorList));
        }

        if (null != courseDTO.getDepartmentList()) {
            List<Department> departmentList = new ArrayList<Department>();
            for (Integer id : courseDTO.getDepartmentList()) {
                Department department = new Department();
                department.setId(id);
                departmentList.add(department);
            }

            course.setDepartmentList(Sets.newHashSet(departmentList));
        }
        courseDAO.saveOrUpdate(course);
    }


    @Override
    public void deleteById(Integer id) {
        courseDAO.deleteById(id);
    }

    @Override
    public PageSupport<OnlineCourseDTO> findPagination(int pageNo, int pageSize) {

        Integer userId = UserUtil.getCurrentUser().getId();
        List<OnlineCourse> courseList = courseDAO.findList(pageNo, pageSize, userId);

        List<OnlineCourseDTO> courseDTOList = getTransformData(courseList);

        return new PageSupport<OnlineCourseDTO>(pageNo, courseList.size(), pageSize, courseDTOList);
    }

    private List<OnlineCourseDTO> getTransformData(List<OnlineCourse> courseList) {
        return transform(courseList, new Function<OnlineCourse, OnlineCourseDTO>() {
            public OnlineCourseDTO apply(OnlineCourse course) {
                return transformToOnlineCourseDTO(course);
            }
        });
    }

    private OnlineCourseDTO transformToOnlineCourseDTO(OnlineCourse course) {
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

    @Override
    public PageSupport<OnlineCourseDTO> findPagination(int pageNo, int pageSize, int userId, String name, Integer status, List<Integer> departments,
                                                       List<Integer> majors) {
        OnlineCourse onlineCourse = new OnlineCourse();
        onlineCourse.setUserId(userId);
        if (null != name) {
            onlineCourse.setName(name);
        }
        if (null != status) {
            onlineCourse.setStatus(OnlineCourse.STATUS.valueOf(status));
        }
        if (null != departments) {
            onlineCourse.setDepartmentList(Sets.newHashSet(transform(departments, new Function<Integer, Department>() {
                @Override
                public Department apply(Integer id) {
                    Department department = new Department();
                    department.setId(id);
                    return department;
                }
            })));
        }
        if (null != majors) {
            onlineCourse.setMajorList(Sets.newHashSet(transform(majors, new Function<Integer, Major>() {
                @Override
                public Major apply(Integer id) {
                    Major major = new Major();
                    major.setId(id);
                    return major;
                }
            })));
        }

//        DetachedCriteria query = DetachedCriteria.forClass(OnlineCourse.class)
//                .add(Property.forName("name").like(name));


        PageSupport data = courseDAO.findPagination(onlineCourse, pageNo, pageSize);
        List<OnlineCourse> list = data.getResultData();
        return new PageSupport<OnlineCourseDTO>(pageNo, list.size(), pageSize, getTransformData(list));
    }

    @Override
    public void updateStatus(Integer id, OnlineCourse.STATUS status) {
        OnlineCourse course = courseDAO.find(id);
        course.setStatus(status);
        courseDAO.saveOrUpdate(course);
    }

    @Override
    public void updateFeature(Integer id, String feature) {
        OnlineCourse course = courseDAO.find(id);
        course.setFeature(feature);
        courseDAO.saveOrUpdate(course);
    }

    @Override
    public void updateScheme(Integer id, String scheme) {
        OnlineCourse course = courseDAO.find(id);
        course.setScheme(scheme);
        courseDAO.saveOrUpdate(course);
    }

    @Override
    public void updateReference(Integer id, String reference) {
        OnlineCourse course = courseDAO.find(id);
        course.setReference(reference);
        courseDAO.saveOrUpdate(course);
    }

    @Override
    public void updatePoint(Integer id, String point) {
        OnlineCourse course = courseDAO.find(id);
        course.setPoint(point);
        courseDAO.saveOrUpdate(course);
    }

    @Override
    public OnlineCourseDTO findById(Integer courseId) {

        OnlineCourseDTO onlineCourseDTO = new OnlineCourseDTO();
        OnlineCourse course = courseDAO.find(courseId);
        onlineCourseDTO.setId(course.getId());
        onlineCourseDTO.setName(course.getName());
        onlineCourseDTO.setDepartmentNameList(transform(Lists.newArrayList(course.getDepartmentList()), new Function<Department, String>() {
            @Override
            public String apply(Department department) {
                return department.getName();
            }
        }));

        onlineCourseDTO.setDepartmentList(transform(Lists.newArrayList(course.getDepartmentList()), new Function<Department, Integer>() {
            @Override
            public Integer apply(Department department) {
                return department.getId();
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
        onlineCourseDTO.setMajorList(transform(Lists.newArrayList(course.getMajorList()), new Function<Major, Integer>() {
            @Override
            public Integer apply(Major major) {
                return major.getId();
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
        onlineCourseDTO.setDescription(course.getDescription());
        onlineCourseDTO.setStatusName(statusName);
        onlineCourseDTO.setRejectReason(course.getRejectReason());
        onlineCourseDTO.setEnableAutoApprove(course.getEnableAutoApprove());
        onlineCourseDTO.setEnablePublic(course.getEnablePublic());
        onlineCourseDTO.setEnableShowInFront(course.getEnableShowInFront());

        onlineCourseDTO.setFeatureDescription(course.getFeature());
        onlineCourseDTO.setSchemeDescription(course.getScheme());
        onlineCourseDTO.setReferenceDescription(course.getReference());
        onlineCourseDTO.setPointDescription(course.getPoint());
        onlineCourseDTO.setEnableCourseUrl(course.getEnableCourseUrl());
        onlineCourseDTO.setEnableDeclareUrl(course.getEnableDeclareUrl());
        onlineCourseDTO.setCourseUrl(course.getCourseUrl());
        onlineCourseDTO.setDeclareUrl(course.getDeclareUrl());
        return onlineCourseDTO;
    }


}
