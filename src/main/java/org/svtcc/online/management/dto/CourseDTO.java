package org.svtcc.online.management.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by hanrenwei on 1/18/15.
 */
public class CourseDTO {
    private Integer id;
    @NotEmpty(message = "课程标题不能为空")
    private String name;

    private String companyName;

    private Integer enabled;

    private String supplement;

    @NotNull(message = "专业不能为空")
    private Integer majorId;
    private String majorName;

    private String departmentName;
    private List<Integer> companyExpertIds;
    private List<String> companyExpertList;
    private List<Integer> schoolExpertIds;
    private List<String> schoolExpertList;
    private List<Integer> courseStandardIds;
    private List<String> courseStandardList;
    private List<Integer> teachingEffectIds;
    private List<String> teachingEffectList;
    private Integer departmentId;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public List<Integer> getCompanyExpertIds() {
        return companyExpertIds;
    }

    public void setCompanyExpertIds(List<Integer> companyExpertIds) {
        this.companyExpertIds = companyExpertIds;
    }

    public List<Integer> getSchoolExpertIds() {
        return schoolExpertIds;
    }

    public void setSchoolExpertIds(List<Integer> schoolExpertIds) {
        this.schoolExpertIds = schoolExpertIds;
    }

    public List<Integer> getCourseStandardIds() {
        return courseStandardIds;
    }

    public void setCourseStandardIds(List<Integer> courseStandardIds) {
        this.courseStandardIds = courseStandardIds;
    }

    public List<Integer> getTeachingEffectIds() {
        return teachingEffectIds;
    }

    public void setTeachingEffectIds(List<Integer> teachingEffectIds) {
        this.teachingEffectIds = teachingEffectIds;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<String> getCompanyExpertList() {
        return companyExpertList;
    }

    public void setCompanyExpertList(List<String> companyExpertList) {
        this.companyExpertList = companyExpertList;
    }

    public List<String> getSchoolExpertList() {
        return schoolExpertList;
    }

    public void setSchoolExpertList(List<String> schoolExpertList) {
        this.schoolExpertList = schoolExpertList;
    }

    public List<String> getCourseStandardList() {
        return courseStandardList;
    }

    public void setCourseStandardList(List<String> courseStandardList) {
        this.courseStandardList = courseStandardList;
    }

    public List<String> getTeachingEffectList() {
        return teachingEffectList;
    }

    public void setTeachingEffectList(List<String> teachingEffectList) {
        this.teachingEffectList = teachingEffectList;
    }
}
