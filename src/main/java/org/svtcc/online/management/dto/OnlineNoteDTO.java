package org.svtcc.online.management.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by hanrenwei on 4/1/15.
 */
public class OnlineNoteDTO {
    private Integer id;
    @NotEmpty(message = "标题不能为空")
    private String title;
    private String content;
    private String comment;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
