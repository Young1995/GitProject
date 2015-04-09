package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.CourseDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.*;
import org.svtcc.online.management.dto.CourseDTO;
import org.svtcc.online.management.service.CourseService;
import org.svtcc.online.management.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created  on 1/11/15.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDAO courseDAO;

    @Override
    public void saveOrUpdate(CourseDTO courseDTO) {
        Course course = new Course();
        Major major = new Major();
        major.setId(courseDTO.getMajorId());
        course.setMajor(major);
        buildCompanyExperts(courseDTO, course);

        buildSchoolExperts(courseDTO, course);

        buildCoursesStandard(courseDTO, course);

        buildTeachingEffects(courseDTO, course);

        course.setName(courseDTO.getName());
        course.setSupplement(courseDTO.getSupplement());
        // 操作详情
        User user = UserUtil.getCurrentUser();
        course.setCreateDate(new Date());
        course.setUpdateDate(new Date());
        course.setCreateUser(user.getUsername());
        course.setUpdateUser(user.getUsername());

        courseDAO.saveOrUpdate(course);
    }

    private void buildTeachingEffects(CourseDTO addCourse, Course course) {
        List<Integer> teachingEffectIds = addCourse.getTeachingEffectIds();
        if (null != teachingEffectIds && teachingEffectIds.size() > 0) {
            List<CompanySetting> teachingEffectsList = new ArrayList<CompanySetting>();
            for (Integer id : teachingEffectIds) {
                CompanySetting courseStandard = new CompanySetting();
                courseStandard.setId(id);
                teachingEffectsList.add(courseStandard);
            }
            course.setTeachingEffectList(teachingEffectsList);
        }
    }

    private void buildCoursesStandard(CourseDTO addCourse, Course course) {
        List<Integer> courseStandardIds = addCourse.getCourseStandardIds();
        if (null != courseStandardIds && courseStandardIds.size() > 0) {
            List<CompanySetting> courseStandardList = new ArrayList<CompanySetting>();
            for (Integer id : courseStandardIds) {
                CompanySetting courseStandard = new CompanySetting();
                courseStandard.setId(id);
                courseStandardList.add(courseStandard);
            }
            course.setCourseStandardList(courseStandardList);
        }
    }

    private void buildSchoolExperts(CourseDTO addCourse, Course course) {
        List<Integer> schoolExpertIds = addCourse.getSchoolExpertIds();
        if (null != schoolExpertIds && schoolExpertIds.size() > 0) {
            List<Expert> schoolExpertList = new ArrayList<Expert>();
            for (Integer id : schoolExpertIds) {
                Expert expert = new Expert();
                expert.setId(id);
                schoolExpertList.add(expert);
            }
            course.setSchoolExpertList(schoolExpertList);
        }
    }

    private void buildCompanyExperts(CourseDTO addCourse, Course course) {
        List<Integer> companyExpertsIds = addCourse.getCompanyExpertIds();
        if (null != companyExpertsIds && companyExpertsIds.size() > 0) {
            List<Expert> companyExpertList = new ArrayList<Expert>();
            for (Integer id : companyExpertsIds) {
                Expert expert = new Expert();
                expert.setId(id);
                companyExpertList.add(expert);
            }
            course.setCompanyExpertList(companyExpertList);
        }
    }

    @Override
    public void deleteById(Integer id) {
        courseDAO.deleteById(id);
    }

    @Override
    public PageSupport<CourseDTO> findPagination(int pageNo, int pageSize) {
        List<CourseDTO> courseDTOList = new ArrayList<CourseDTO>();

        List<Course> courseList = courseDAO.findList(pageNo, pageSize);
        for (Course course : courseList) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            courseDTO.setCompanyName(course.getCompanyExpertList() != null ? course.getCompanyExpertList().get(0).getDepartmentName() : "");
            courseDTO.setMajorName(course.getMajor() != null ? course.getMajor().getName() : "");
            courseDTO.setEnabled(course.getEnabled());
            courseDTOList.add(courseDTO);
        }

        return new PageSupport<CourseDTO>(pageNo, courseList.size(), pageSize, courseDTOList);
    }

    @Override
    public CourseDTO findById(Integer courseId) {
        CourseDTO courseDTO = new CourseDTO();
        Course course = courseDAO.find(courseId);
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setSupplement(course.getSupplement());
        Optional<Major> major = Optional.fromNullable(course.getMajor());
        if (major.isPresent()) {
            courseDTO.setMajorName(major.get().getName());
            Optional<Department> department = Optional.fromNullable(major.get().getDepartment());
            if (department.isPresent()) {
                courseDTO.setDepartmentName(department.get().getName());
            }

        }
        Optional<List<Expert>> schoolExpertList = Optional.fromNullable(course.getSchoolExpertList());
        if (schoolExpertList.isPresent()) {
            courseDTO.setSchoolExpertList(transform(schoolExpertList.get(), new Function<Expert, String>() {
                public String apply(Expert course) {
                    return course.getExpertName();
                }
            }));
        }
        Optional<List<Expert>> companyExpertList = Optional.fromNullable(course.getCompanyExpertList());
        if (companyExpertList.isPresent()) {
            courseDTO.setCompanyExpertList(transform(companyExpertList.get(), new Function<Expert, String>() {
                public String apply(Expert course) {
                    return course.getExpertName();
                }
            }));
        }


        Optional<List<CompanySetting>> courseStandardList = Optional.fromNullable(course.getCourseStandardList());
        if (courseStandardList.isPresent()) {
            courseDTO.setCourseStandardList(transform(courseStandardList.get(), new Function<CompanySetting, String>() {
                public String apply(CompanySetting companySetting) {
                    return companySetting.getSettingName();
                }
            }));
        }

        Optional<List<CompanySetting>> teachingEffectList = Optional.fromNullable(course.getCourseStandardList());
        if (teachingEffectList.isPresent()) {
            courseDTO.setTeachingEffectList(transform(teachingEffectList.get(), new Function<CompanySetting, String>() {
                public String apply(CompanySetting companySetting) {
                    return companySetting.getSettingName();
                }
            }));
        }


        return courseDTO;
    }
}
