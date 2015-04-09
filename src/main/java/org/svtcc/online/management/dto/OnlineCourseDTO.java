package org.svtcc.online.management.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by hanrenwei on 1/18/15.
 */
public class OnlineCourseDTO {
    private Integer id;
    @NotEmpty(message = "课程标题不能为空")
    private String name;
    private List<Integer> majorList;
    private List<Integer> departmentList;
    private List<Integer> gradeList;
    private List<Integer> excludeUserList;
    private List<String> teacherNameList;
    private List<String> departmentNameList;
    private List<String> majorNameList;
    private Boolean enablePublic;
    private Boolean enableShowInFront;
    private Boolean enableDeclareUrl;
    private Boolean enableCourseUrl;
    private Boolean enableAutoApprove;
    private String declareUrl;
    private String courseUrl;
    private String description;
    private String featureDescription;
    private String referenceDescription;
    private String schemeDescription;
    private String pointDescription;
    private Integer status;
    private String rejectReason;
    private String statusName;

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

    public List<Integer> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Integer> majorList) {
        this.majorList = majorList;
    }

    public List<Integer> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Integer> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Integer> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Integer> gradeList) {
        this.gradeList = gradeList;
    }

    public List<Integer> getExcludeUserList() {
        return excludeUserList;
    }

    public void setExcludeUserList(List<Integer> excludeUserList) {
        this.excludeUserList = excludeUserList;
    }

    public Boolean getEnablePublic() {
        return enablePublic;
    }

    public void setEnablePublic(Boolean enablePublic) {
        this.enablePublic = enablePublic;
    }

    public Boolean getEnableShowInFront() {
        return enableShowInFront;
    }

    public void setEnableShowInFront(Boolean enableShowInFront) {
        this.enableShowInFront = enableShowInFront;
    }

    public Boolean getEnableAutoApprove() {
        return enableAutoApprove;
    }

    public void setEnableAutoApprove(Boolean enableAutoApprove) {
        this.enableAutoApprove = enableAutoApprove;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getTeacherNameList() {
        return teacherNameList;
    }

    public void setTeacherNameList(List<String> teacherNameList) {
        this.teacherNameList = teacherNameList;
    }

    public List<String> getDepartmentNameList() {
        return departmentNameList;
    }

    public void setDepartmentNameList(List<String> departmentNameList) {
        this.departmentNameList = departmentNameList;
    }

    public List<String> getMajorNameList() {
        return majorNameList;
    }

    public void setMajorNameList(List<String> majorNameList) {
        this.majorNameList = majorNameList;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeclareUrl() {
        return declareUrl;
    }

    public void setDeclareUrl(String declareUrl) {
        this.declareUrl = declareUrl;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public Boolean getEnableDeclareUrl() {
        return enableDeclareUrl;
    }

    public void setEnableDeclareUrl(Boolean enableDeclareUrl) {
        this.enableDeclareUrl = enableDeclareUrl;
    }

    public Boolean getEnableCourseUrl() {
        return enableCourseUrl;
    }

    public void setEnableCourseUrl(Boolean enableCourseUrl) {
        this.enableCourseUrl = enableCourseUrl;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    public String getPointDescription() {
        return pointDescription;
    }

    public void setPointDescription(String pointDescription) {
        this.pointDescription = pointDescription;
    }
}
