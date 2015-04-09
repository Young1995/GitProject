package org.svtcc.online.management.dto;


import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by hanrenwei on 4/1/15.
 */
public class CommonQuestionDTO {
    private Integer id;
    @NotEmpty(message = "问题内容不能为空")
    private String content;
    private String comment;
    @NotEmpty(message = "问题回答不能为空")
    private String answer;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
