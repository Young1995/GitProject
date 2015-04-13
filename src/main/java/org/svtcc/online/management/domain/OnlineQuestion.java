package org.svtcc.online.management.domain;

import javax.persistence.*;

/**
 * Created by hanrenwei on 4/1/15.
 */
@SuppressWarnings("serial")
@Entity(name = "OnlineQuestion")
@Table(name = "svtcc_online_question")
public class OnlineQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @Column(name = "comment")
    private String comment;
    @Column(name = "userId")
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
