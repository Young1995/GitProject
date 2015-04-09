package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.OnlineCourseDAO;
import org.svtcc.online.management.dao.StudentOnlineCourseDAO;
import org.svtcc.online.management.dao.UserDao;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.*;
import org.svtcc.online.management.dto.OnlineCourseDTO;
import org.svtcc.online.management.dto.StudentOnlineCourseDTO;
import org.svtcc.online.management.dto.UserDTO;
import org.svtcc.online.management.service.OnlineCoursePrincipalService;
import org.svtcc.online.management.service.StudentOnlineCourseService;
import org.svtcc.online.management.util.UserUtil;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created by hanrenwei on 3/17/15.
 */
@Service
public class StudentOnlineCourseServiceImpl implements StudentOnlineCourseService {
    @Autowired
    private StudentOnlineCourseDAO studentOnlineCourseDAO;
    @Autowired
    private OnlineCourseDAO onlineCourseDAO;

    @Autowired
    private OnlineCoursePrincipalService onlineCoursePrincipalService;

    @Autowired
    private UserDao userDao;

    @Override
    public void saveOrUpdate(StudentOnlineCourseDTO studentOnlineCourseDTO) {

    }

    @Override
    public void deleteById(Integer id) {
        studentOnlineCourseDAO.deleteById(id);
    }

    @Override
    public PageSupport<StudentOnlineCourseDTO> findPagination(int pageNo, int pageSize) {
        List<StudentOnlineCourse> onlineCourseList = studentOnlineCourseDAO.findList(pageNo, pageSize);
        List<StudentOnlineCourseDTO> results = getTransformData(onlineCourseList);
        return new PageSupport<StudentOnlineCourseDTO>(pageNo, results.size(), pageSize, results);
    }


    private List<StudentOnlineCourseDTO> getTransformData(List<StudentOnlineCourse> courseList) {
        return transform(courseList, new Function<StudentOnlineCourse, StudentOnlineCourseDTO>() {
            public StudentOnlineCourseDTO apply(StudentOnlineCourse studentOnlineCourse) {
                StudentOnlineCourseDTO studentOnlineCourseDTO = new StudentOnlineCourseDTO();
                studentOnlineCourseDTO.setId(studentOnlineCourse.getId());
                OnlineCourseDTO onlineCourseDTO = ServiceUtils.transformToOnlineCourseDTO(studentOnlineCourse.getOnlineCourse());
                studentOnlineCourseDTO.setOnlineCourse(onlineCourseDTO);
                studentOnlineCourseDTO.setStatus(studentOnlineCourse.getStatus());
                return studentOnlineCourseDTO;
            }
        });
    }

    @Override
    public PageSupport<StudentOnlineCourseDTO> findPagination(int pageNo, int pageSize, int userId, String name, Integer status, List<Integer> departments,
                                                              List<Integer> majors) {
        OnlineCourse onlineCourse = new OnlineCourse();
        if (null != name) {
            onlineCourse.setName(name);
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

        StudentOnlineCourse studentOnlineCourse = new StudentOnlineCourse();
        studentOnlineCourse.setOnlineCourse(onlineCourse);
        if (null != status) {
            studentOnlineCourse.setStatus(StudentOnlineCourse.Status.valueOf(status));
        }

        User user = new User();
        user.setId(userId);
        studentOnlineCourse.setUser(user);
        List<StudentOnlineCourse> studentOnlineCourses = studentOnlineCourseDAO.findByExample(studentOnlineCourse);
        List<StudentOnlineCourseDTO> studentOnlineCourseDTOList = transform(studentOnlineCourses, new Function<StudentOnlineCourse, StudentOnlineCourseDTO>() {
            @Override
            public StudentOnlineCourseDTO apply(StudentOnlineCourse onlineCourse) {
                OnlineCourse course = onlineCourseDAO.find(onlineCourse.getOnlineCourse().getId());
                StudentOnlineCourseDTO studentOnlineCourseDTO = new StudentOnlineCourseDTO();
                OnlineCourseDTO onlineCourseDTO = transformToOnlineCourseDTO(course);
                studentOnlineCourseDTO.setOnlineCourse(onlineCourseDTO);
                studentOnlineCourseDTO.setDepartments(onlineCourseDTO.getDepartmentNameList());
                studentOnlineCourseDTO.setMajors(onlineCourseDTO.getMajorNameList());
                studentOnlineCourseDTO.setTeachers(onlineCourseDTO.getTeacherNameList());
                studentOnlineCourseDTO.setId(onlineCourse.getId());
                studentOnlineCourseDTO.setStatus(onlineCourse.getStatus());
                User user = onlineCourse.getUser();
                if(user!=null){
                    UserDTO student = new UserDTO();
                    student.setName(user.getName());
                    studentOnlineCourseDTO.setStudent(student);
                }

                return studentOnlineCourseDTO;
            }
        });

        return new PageSupport<StudentOnlineCourseDTO>(pageNo, studentOnlineCourseDTOList.size(), pageSize, studentOnlineCourseDTOList);
    }

    @Override
    public void updateStatus(Integer courseId, StudentOnlineCourse.Status status) {
        StudentOnlineCourse studentOnlineCourse = new StudentOnlineCourse();
        studentOnlineCourse.setOnlineCourse(onlineCourseDAO.find(courseId));
        List<StudentOnlineCourse> studentOnlineCourses = studentOnlineCourseDAO.findByProperty("onlineCourse.id", courseId);
        if (!studentOnlineCourses.isEmpty()) {
            studentOnlineCourse = studentOnlineCourses.get(0);
        }
        studentOnlineCourse.setStatus(status);
        studentOnlineCourseDAO.saveOrUpdate(studentOnlineCourse);
    }

    @Override
    public void selectCourse(Integer courseId, StudentOnlineCourse.Status status) {
        StudentOnlineCourse studentOnlineCourse = new StudentOnlineCourse();
        studentOnlineCourse.setOnlineCourse(onlineCourseDAO.find(courseId));
        studentOnlineCourse.setUser(userDao.find(UserUtil.getCurrentUser().getId()));
        List<StudentOnlineCourse> studentOnlineCourses = studentOnlineCourseDAO.findByProperty("onlineCourse.id", courseId);
        if (!studentOnlineCourses.isEmpty()) {
            studentOnlineCourse = studentOnlineCourses.get(0);
        }
        studentOnlineCourse.setStatus(status);
        studentOnlineCourseDAO.saveOrUpdate(studentOnlineCourse);
    }

    @Override
    public StudentOnlineCourseDTO findById(Integer id) {
        return transformToStudentOnlineCourseDTO(studentOnlineCourseDAO.find(id));
    }

    @Override
    public PageSupport<StudentOnlineCourseDTO> findPagination(int pageNo, int pageSize, int userId, String name, List<Integer> departments, List<Integer> majors) {
        OnlineCourse onlineCourse = new OnlineCourse();
        if (null != name) {
            onlineCourse.setName(name);
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
        List<OnlineCourse> onlineCourses = onlineCourseDAO.findList(pageNo, pageSize, userId, name);
//        List<OnlineCourse> onlineCourses = onlineCourseDAO.findPagination(onlineCourse, pageNo, pageSize).getResultData();
        List<StudentOnlineCourseDTO> studentOnlineCourseDTOList = transform(onlineCourses, new Function<OnlineCourse, StudentOnlineCourseDTO>() {
            @Override
            public StudentOnlineCourseDTO apply(OnlineCourse onlineCourse) {
                StudentOnlineCourseDTO studentOnlineCourseDTO = new StudentOnlineCourseDTO();
                OnlineCourseDTO onlineCourseDTO = transformToOnlineCourseDTO(onlineCourse);
                studentOnlineCourseDTO.setOnlineCourse(onlineCourseDTO);
                studentOnlineCourseDTO.setDepartments(onlineCourseDTO.getDepartmentNameList());
                studentOnlineCourseDTO.setMajors(onlineCourseDTO.getMajorNameList());
                studentOnlineCourseDTO.setTeachers(onlineCourseDTO.getTeacherNameList());
                List<StudentOnlineCourse> studentOnlineCourses = studentOnlineCourseDAO.findByProperty("onlineCourse.id", onlineCourse.getId());
                if (studentOnlineCourses.size() > 0) {
                    studentOnlineCourseDTO.setId(studentOnlineCourses.get(0).getId());
                    studentOnlineCourseDTO.setStatus(studentOnlineCourses.get(0).getStatus());
                    User user = studentOnlineCourses.get(0).getUser();
                    UserDTO student = new UserDTO();
                    student.setName(user.getName());
                    studentOnlineCourseDTO.setStudent(student);
                }
                return studentOnlineCourseDTO;
            }
        });
        return new PageSupport<StudentOnlineCourseDTO>(pageNo, studentOnlineCourseDTOList.size(), pageSize, studentOnlineCourseDTOList);
    }

    private StudentOnlineCourseDTO transformToStudentOnlineCourseDTO(StudentOnlineCourse studentOnlineCourse) {
        StudentOnlineCourseDTO studentOnlineCourseDTO = new StudentOnlineCourseDTO();
        studentOnlineCourseDTO.setId(studentOnlineCourse.getId());

        if (null != studentOnlineCourse.getOnlineCourse()) {
            List<Department> departments = Lists.newArrayList(studentOnlineCourse.getOnlineCourse().getDepartmentList());
            studentOnlineCourseDTO.setDepartments(transform(departments, new Function<Department, String>() {
                @Override
                public String apply(Department department) {
                    return department.getName();
                }
            }));
            List<Major> majors = Lists.newArrayList(studentOnlineCourse.getOnlineCourse().getMajorList());
            studentOnlineCourseDTO.setMajors(transform(majors, new Function<Major, String>() {
                @Override
                public String apply(Major major) {
                    return major.getName();
                }
            }));

            List<OnlineCoursePrincipal> teachers = Lists.newArrayList(studentOnlineCourse.getOnlineCourse().getOnlineCoursePrincipallList());
            studentOnlineCourseDTO.setTeachers(transform(teachers, new Function<OnlineCoursePrincipal, String>() {
                @Override
                public String apply(OnlineCoursePrincipal principal) {
                    return principal.getName();
                }
            }));
            studentOnlineCourseDTO.setEnableAutoApprove(studentOnlineCourse.getOnlineCourse().getEnableAutoApprove());
            studentOnlineCourseDTO.setStatus(studentOnlineCourse.getStatus());
        }


        return studentOnlineCourseDTO;
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
        else if (course.getStatus().equals(OnlineCourse.STATUS.PUBLISHED)) {
            statusName = "已发布";
        }
        onlineCourseDTO.setStatusName(statusName);
        onlineCourseDTO.setRejectReason(course.getDescription());
        return onlineCourseDTO;
    }
}
