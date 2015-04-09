package org.svtcc.online.management.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity(name = "AttendanceSetting")
@Table(name = "svtcc_attendance_setting")
public class AttendanceSetting implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "display_course_name")
	private boolean displayCourseName = true;

	@Column(name = "display_begin_time")
	private boolean displayBeginTime = true;

	@Column(name = "display_end_time")
	private boolean displayEndTime = true;

	@Column(name = "display_classroom")
	private boolean displayClassRoom = true;

	@Column(name = "display_total_student")
	private boolean displayTotalStudent = true;

	@Column(name = "display_actual_student")
	private boolean displayActualStudent = true;

	@Column(name = "display_late_student")
	private boolean displayLateStudent = true;

	@Column(name = "display_absent_student")
	private boolean displayAbsentStudent = true;

	@Column(name = "display_type")
	private Integer type; // 1. 教师 2. 班级 3. 学生

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

	public boolean isDisplayCourseName() {
		return displayCourseName;
	}

	public void setDisplayCourseName(boolean displayCourseName) {
		this.displayCourseName = displayCourseName;
	}

	public boolean isDisplayBeginTime() {
		return displayBeginTime;
	}

	public void setDisplayBeginTime(boolean displayBeginTime) {
		this.displayBeginTime = displayBeginTime;
	}

	public boolean isDisplayEndTime() {
		return displayEndTime;
	}

	public void setDisplayEndTime(boolean displayEndTime) {
		this.displayEndTime = displayEndTime;
	}

	public boolean isDisplayClassRoom() {
		return displayClassRoom;
	}

	public void setDisplayClassRoom(boolean displayClassRoom) {
		this.displayClassRoom = displayClassRoom;
	}

	public boolean isDisplayTotalStudent() {
		return displayTotalStudent;
	}

	public void setDisplayTotalStudent(boolean displayTotalStudent) {
		this.displayTotalStudent = displayTotalStudent;
	}

	public boolean isDisplayActualStudent() {
		return displayActualStudent;
	}

	public void setDisplayActualStudent(boolean displayActualStudent) {
		this.displayActualStudent = displayActualStudent;
	}

	public boolean isDisplayLateStudent() {
		return displayLateStudent;
	}

	public void setDisplayLateStudent(boolean displayLateStudent) {
		this.displayLateStudent = displayLateStudent;
	}

	public boolean isDisplayAbsentStudent() {
		return displayAbsentStudent;
	}

	public void setDisplayAbsentStudent(boolean displayAbsentStudent) {
		this.displayAbsentStudent = displayAbsentStudent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

}
