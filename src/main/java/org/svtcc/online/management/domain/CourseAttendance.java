package org.svtcc.online.management.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity(name = "CourseAttendance")
@Table(name = "svtcc_course_attendance")
public class CourseAttendance implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "attend_no")
	private String attendNo;

	/**
	 * the details that administrator set the settings.
	 */
	@Column(name = "total_students")
	private Integer totalStudent;

	@Column(name = "actual_students")
	private Integer actualStudent;

	@Column(name = "late_students")
	private Integer lateStudent;

	@Column(name = "absent_students")
	private Integer absentStudent;

	@Column(name = "begin_time")
	private String beginTime;

	@Column(name = "end_time")
	private String endTime;

	@Column(name = "pub_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pubDate; // when generate this record, it

	@Column(name = "finish_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishDate;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "checked")
	private boolean checked = false;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name = "course_table_info_id")
	private CourseTableInfo course;

	@Column(name = "check_user_id")
	private Integer checkUserId;

	@Column(name = "check_user_name")
	private String checkUserName;

	@Column(name = "check_role")
	private Integer checkRole; // check role. 1: teacher, 2. class monitor, 3.
								// student.
	@Transient
	private String attendRate;

	/**
	 * 操作记录的时间和用户
	 */
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "update_user")
	private String updateUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotalStudent() {
		return totalStudent;
	}

	public void setTotalStudent(Integer totalStudent) {
		this.totalStudent = totalStudent;
	}

	public Integer getActualStudent() {
		return actualStudent;
	}

	public void setActualStudent(Integer actualStudent) {
		this.actualStudent = actualStudent;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public CourseTableInfo getCourse() {
		return course;
	}

	public void setCourse(CourseTableInfo course) {
		this.course = course;
	}

	public Integer getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Integer checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getAttendNo() {
		return attendNo;
	}

	public void setAttendNo(String attendNo) {
		this.attendNo = attendNo;
	}

	public Integer getCheckRole() {
		return checkRole;
	}

	public void setCheckRole(Integer checkRole) {
		this.checkRole = checkRole;
	}

	public Integer getLateStudent() {
		return lateStudent;
	}

	public void setLateStudent(Integer lateStudent) {
		this.lateStudent = lateStudent;
	}

	public Integer getAbsentStudent() {
		return absentStudent;
	}

	public void setAbsentStudent(Integer absentStudent) {
		this.absentStudent = absentStudent;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getAttendRate() {
		if(getTotalStudent() != null && getTotalStudent() != 0) {
			float rate = (Float.parseFloat(getActualStudent() + "") / Float.parseFloat(getTotalStudent() + "")) * 100;;
			attendRate = String.format("%.2f", rate);
		} else {
			attendRate = "0";
		}
		return attendRate;
	}

	public void setAttendRate(String attendRate) {
		this.attendRate = attendRate;
	}

}
