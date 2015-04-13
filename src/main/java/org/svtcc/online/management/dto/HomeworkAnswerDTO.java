package org.svtcc.online.management.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class HomeworkAnswerDTO {
	private Integer questionId;
	private Integer homeworkId;
	private Integer singleSelectionAnswer;
	private Integer selectionAnswer_1;
	private Integer selectionAnswer_2;
	private Integer selectionAnswer_3;
	private Integer selectionAnswer_4;
	private String answer;
	private Integer judgeAnswer;
	private Integer type;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Integer getSingleSelectionAnswer() {
		return singleSelectionAnswer;
	}

	public void setSingleSelectionAnswer(Integer singleSelectionAnswer) {
		this.singleSelectionAnswer = singleSelectionAnswer;
	}

	public Integer getSelectionAnswer_1() {
		return selectionAnswer_1;
	}

	public void setSelectionAnswer_1(Integer selectionAnswer_1) {
		this.selectionAnswer_1 = selectionAnswer_1;
	}

	public Integer getSelectionAnswer_2() {
		return selectionAnswer_2;
	}

	public void setSelectionAnswer_2(Integer selectionAnswer_2) {
		this.selectionAnswer_2 = selectionAnswer_2;
	}

	public Integer getSelectionAnswer_3() {
		return selectionAnswer_3;
	}

	public void setSelectionAnswer_3(Integer selectionAnswer_3) {
		this.selectionAnswer_3 = selectionAnswer_3;
	}

	public Integer getSelectionAnswer_4() {
		return selectionAnswer_4;
	}

	public void setSelectionAnswer_4(Integer selectionAnswer_4) {
		this.selectionAnswer_4 = selectionAnswer_4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getJudgeAnswer() {
		return judgeAnswer;
	}

	public void setJudgeAnswer(Integer judgeAnswer) {
		this.judgeAnswer = judgeAnswer;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
