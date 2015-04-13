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
@Entity(name = "HomeworkDetail")
@Table(name = "svtcc_homework_detail")
public class HomeworkDetail implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "question_title")
	private String questionTitle;

	@Column(name = "question_type")
	private Integer questionType; // 1. 单选 2. 多选 3. 判断正误 4. 填空题 5. 简答题

	// 填空题和简答题的答案
	@Column(name = "answer")
	private String answer;

	// 4个选项
	@Column(name = "selection_1")
	private String selection_1;

	@Column(name = "selection_2")
	private String selection_2;

	@Column(name = "selection_3")
	private String selection_3;

	@Column(name = "selection_4")
	private String selection_4;

	// 选择题答案(单选为一个， 多选为多个)
	@Column(name = "selction_answer_1")
	private boolean selctionAnswer_1;

	@Column(name = "selction_answer_2")
	private boolean selctionAnswer_2;

	@Column(name = "selction_answer_3")
	private boolean selctionAnswer_3;

	@Column(name = "selction_answer_4")
	private boolean selctionAnswer_4;

	// 判断题答案
	@Column(name = "judge_answer")
	private boolean judgeAnswer;

	@Column(name = "belong_user_id")
	private Integer belongUserId;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "homework_id")
	private Homework homework;

	@Transient
	private Integer homeworkId;

	@Transient
	private String typeName;

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

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSelection_1() {
		return selection_1;
	}

	public void setSelection_1(String selection_1) {
		this.selection_1 = selection_1;
	}

	public String getSelection_2() {
		return selection_2;
	}

	public void setSelection_2(String selection_2) {
		this.selection_2 = selection_2;
	}

	public String getSelection_3() {
		return selection_3;
	}

	public void setSelection_3(String selection_3) {
		this.selection_3 = selection_3;
	}

	public String getSelection_4() {
		return selection_4;
	}

	public void setSelection_4(String selection_4) {
		this.selection_4 = selection_4;
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

	public boolean isJudgeAnswer() {
		return judgeAnswer;
	}

	public void setJudgeAnswer(boolean judgeAnswer) {
		this.judgeAnswer = judgeAnswer;
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

	public Integer getBelongUserId() {
		return belongUserId;
	}

	public void setBelongUserId(Integer belongUserId) {
		this.belongUserId = belongUserId;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getTypeName() {
		if(questionType == 1) {
			typeName = "单选题";
		} else if(questionType == 2) {
			typeName = "多选题";
		} else if(questionType == 3) {
			typeName = "判断题";
		} else if(questionType == 4) {
			typeName = "填空题";
		} else if(questionType == 5) {
			typeName = "简答题";
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
