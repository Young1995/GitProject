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
@Entity(name = "HomeworkTypeSetting")
@Table(name = "svtcc_homework_type_setting")
public class HomeworkTypeSetting implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "display_single_select")
	private boolean singleSelect;

	@Column(name = "display_multi_select")
	private boolean multiSelect;

	@Column(name = "display_judge_question")
	private boolean judgeQuestion;

	@Column(name = "display_fill_question")
	private boolean fillQuestion;

	@Column(name = "display_desc_question")
	private boolean descQuestion;

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

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public boolean isJudgeQuestion() {
		return judgeQuestion;
	}

	public void setJudgeQuestion(boolean judgeQuestion) {
		this.judgeQuestion = judgeQuestion;
	}

	public boolean isFillQuestion() {
		return fillQuestion;
	}

	public void setFillQuestion(boolean fillQuestion) {
		this.fillQuestion = fillQuestion;
	}

	public boolean isDescQuestion() {
		return descQuestion;
	}

	public void setDescQuestion(boolean descQuestion) {
		this.descQuestion = descQuestion;
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
