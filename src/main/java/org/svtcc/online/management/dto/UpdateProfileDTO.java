package org.svtcc.online.management.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created  on 1/2/15.
 */
public class UpdateProfileDTO {
	@NotEmpty(message = "用户名不能为空")
	@Size(min = 6, message = "用户名至少为6位字母、数字、下划线等")
	private String username; // 用户名

	@NotEmpty(message = "姓名不能为空")
	private String name; // 学生姓名 或者 教师姓名

	@NotEmpty(message = "学号/工号不能为空")
	@Size(min = 6, message = "学号/工号至少为6位")
	private String positionNo; // 学生学号 或 教师工号

	private boolean gender; // 用户性别

	private String phoneNumber;

	@NotEmpty(message = "邮箱不能为空")
	@Email(message = "邮箱格式不正确")
	private String email; // 用户邮箱

	private int department; // 用户所属学院,

	private String description;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
