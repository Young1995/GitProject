package org.svtcc.online.management.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity(name = "HomeworkAnswer")
@Table(name = "svtcc_homework_answer")
public class HomeworkAnswer implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private HomeworkDetail question; // can find the origin question
										// information, include: question title,
										// question answer, question type

	@Column(name = "selction_answer_1")
	private boolean selctionAnswer_1;

	@Column(name = "selction_answer_2")
	private boolean selctionAnswer_2;

	@Column(name = "selction_answer_3")
	private boolean selctionAnswer_3;

	@Column(name = "selction_answer_4")
	private boolean selctionAnswer_4;

	@Column(name = "answer")
	private String answer;

	@Column(name = "judge_answer")
	private boolean judgeAnswer;

	@Column(name = "student_id")
	private Integer studentId;

	@Transient
	private Integer homeworkId;

	@Transient
	private Integer questionId;

	@Transient
	private Integer singleSelectionAnswer;

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

	public HomeworkDetail getQuestion() {
		return question;
	}

	public void setQuestion(HomeworkDetail question) {
		this.question = question;
	}

	public boolean isSelctionAnswer_1() {
		return selctionAnswer_1;
	}

	public void setSelctionAnswer_1(boolean selctionAnswer_1) {
		this.selctionAnswer_1 = selctionAnswer_1;
	}

	public boolean isSelctionAnswer_2() {
		return selctionAnswer_2;
	}

	public void setSelctionAnswer_2(boolean selctionAnswer_2) {
		this.selctionAnswer_2 = selctionAnswer_2;
	}

	public boolean isSelctionAnswer_3() {
		return selctionAnswer_3;
	}

	public void setSelctionAnswer_3(boolean selctionAnswer_3) {
		this.selctionAnswer_3 = selctionAnswer_3;
	}

	public boolean isSelctionAnswer_4() {
		return selctionAnswer_4;
	}

	public void setSelctionAnswer_4(boolean selctionAnswer_4) {
		this.selctionAnswer_4 = selctionAnswer_4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isJudgeAnswer() {
		return judgeAnswer;
	}

	public void setJudgeAnswer(boolean judgeAnswer) {
		this.judgeAnswer = judgeAnswer;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSingleSelectionAnswer() {
		return singleSelectionAnswer;
	}

	public void setSingleSelectionAnswer(Integer singleSelectionAnswer) {
		this.singleSelectionAnswer = singleSelectionAnswer;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
