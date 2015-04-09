package org.svtcc.online.management.dto;

import org.svtcc.online.management.domain.StudentOnlineCourse;

import java.util.List;

/**
 * Created by hanrenwei on 3/17/15.
 */
public class StudentOnlineCourseDTO {
    private Integer id;
    private OnlineCourseDTO onlineCourse;
    private UserDTO student;
    private List<String> departments;
    private List<String> majors;
    private List<String> teachers;
    private String statusName;
    private StudentOnlineCourse.Status status;
    private boolean enableAutoApprove;


    public OnlineCourseDTO getOnlineCourse() {
        return onlineCourse;
    }

    public void setOnlineCourse(OnlineCourseDTO onlineCourse) {
        this.onlineCourse = onlineCourse;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public StudentOnlineCourse.Status getStatus() {
        return status;
    }

    public void setStatus(StudentOnlineCourse.Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public boolean isEnableAutoApprove() {
        return enableAutoApprove;
    }

    public void setEnableAutoApprove(boolean enableAutoApprove) {
        this.enableAutoApprove = enableAutoApprove;
    }

    public String getStatusName() {
        String statusName = "未选择";
        if (StudentOnlineCourse.Status.UNSELECTED.equals(status)) {
            statusName = "未选择";
        } else if (StudentOnlineCourse.Status.SELECTED.equals(status)) {
            statusName = "已选择";
        } else if (StudentOnlineCourse.Status.PENDING.equals(status)) {
            statusName = "待审核";
        } else if (StudentOnlineCourse.Status.APPROVED.equals(status)) {
            statusName = "通过审核";
        } else if (StudentOnlineCourse.Status.REJECTED.equals(status)) {
            statusName = "未通过审核";
        }
        return statusName;
    }

    public UserDTO getStudent() {
        return student;
    }

    public void setStudent(UserDTO student) {
        this.student = student;
    }
}
