package org.svtcc.online.management.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created on 2/9/15.
 */
@SuppressWarnings("serial")
@Entity(name = "OnlineCourse")
@Table(name = "svtcc_online_course")
public class OnlineCourse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "userId")
    private Integer userId;
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "svtcc_online_course_departments",
            joinColumns = {@JoinColumn(name = "online_course_id")},
            inverseJoinColumns = {@JoinColumn(name = "department_id")})
    private Set<Department> departmentList;
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "svtcc_online_course_majors",
            joinColumns = {@JoinColumn(name = "online_course_id")},
            inverseJoinColumns = {@JoinColumn(name = "major_id")})
    private Set<Major> majorList;
    @OneToMany(cascade = {CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "svtcc_online_course_grades",
            joinColumns = {@JoinColumn(name = "online_course_id")},
            inverseJoinColumns = {@JoinColumn(name = "grade_id")})
    private List<Grade> gradeList;
    @OneToMany(cascade = {CascadeType.ALL,
            CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(name = "svtcc_online_course_users",
            joinColumns = {@JoinColumn(name = "online_course_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> excludeUserList;

    @OneToMany(mappedBy = "onlineCourse", cascade = {CascadeType.ALL,
            CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<OnlineCoursePrincipal> onlineCoursePrincipallList;
    @OneToMany(mappedBy = "onlineCourse", cascade = {CascadeType.ALL,
            CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Document> documentList;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<StudentOnlineCourse> studentOnlineCourseList;

    @Column(name = "enablePublic")
    private Boolean enablePublic;
    @Column(name = "enableShowInFront")
    private Boolean enableShowInFront;
    @Column(name = "enableAutoApprove")
    private Boolean enableAutoApprove;
    @Column(name = "status")
    private STATUS status;
    @Column(name = "description")
    private String description;
    @Column(name = "feature")
    private String feature;
    @Column(name = "scheme")
    private String scheme;
    @Column(name = "reference")
    private String reference;
    @Column(name = "point")
    private String point;
    @Column(name = "declareUrl")
    private String declareUrl;
    @Column(name = "courseUrl")
    private String courseUrl;
    @Column(name = "enableDeclareUrl")
    private Boolean enableDeclareUrl;
    @Column(name = "enableCourseUrl")
    private Boolean enableCourseUrl;
    @Column(name = "rejectReason")
    private String rejectReason;


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

    public Set<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(Set<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public Set<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(Set<Major> majorList) {
        this.majorList = majorList;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public List<User> getExcludeUserList() {
        return excludeUserList;
    }

    public void setExcludeUserList(List<User> excludeUserList) {
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

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
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

    public List<OnlineCoursePrincipal> getOnlineCoursePrincipallList() {
        return onlineCoursePrincipallList;
    }

    public void setOnlineCoursePrincipallList(List<OnlineCoursePrincipal> onlineCoursePrincipallList) {
        this.onlineCoursePrincipallList = onlineCoursePrincipallList;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public enum STATUS {
        PROCESSING(1), UNAPPROVED(2), UNPUBLISHED(3), PUBLISHED(4);
        private int value = 0;

        private STATUS(int value) {
            this.value = value;
        }

        public static STATUS valueOf(int value) {
            switch (value) {
                case 1:
                    return PROCESSING;
                case 2:
                    return UNAPPROVED;
                case 3:
                    return UNPUBLISHED;
                case 4:
                    return PUBLISHED;
                default:
                    return UNAPPROVED;
            }
        }

        public int value() {
            return this.value;
        }
    }


//    public Set<StudentOnlineCourse> getStudentOnlineCourseList() {
//        return studentOnlineCourseList;
//    }

//    public void setStudentOnlineCourseList(Set<StudentOnlineCourse> studentOnlineCourseList) {
//        this.studentOnlineCourseList = studentOnlineCourseList;
//    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
