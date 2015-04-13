package org.svtcc.online.management.dto;

import java.util.List;

/**
 * Created by hanrenwei on 3/12/15.
 */
public class SearchOnlineCourseDTO {
    String name;
    Integer status;
    List<Integer> departments;
    List<Integer> majors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Integer> departments) {
        this.departments = departments;
    }

    public List<Integer> getMajors() {
        return majors;
    }

    public void setMajors(List<Integer> majors) {
        this.majors = majors;
    }
}
