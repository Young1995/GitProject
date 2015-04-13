package org.svtcc.online.management.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by hanrenwei on 3/17/15.
 */
@SuppressWarnings("serial")
@Entity(name = "StudentOnlineCourse")
@Table(name = "svtcc_student_online_course")
public class StudentOnlineCourse implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "online_course_id")
    private OnlineCourse onlineCourse;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "status")
    private Status status;

    public enum Status {
        UNSELECTED(1), PENDING(2), SELECTED(3),APPROVED(4),REJECTED(5);
        private int value = 0;

        private Status(int value) {
            this.value = value;
        }

        public static Status valueOf(int value) {
            switch (value) {
                case 1:
                    return UNSELECTED;
                case 2:
                    return PENDING;
                case 3:
                    return SELECTED;
                case 4:
                    return APPROVED;
                case 5:
                    return REJECTED;
                default:
                    return UNSELECTED;
            }
        }

        public int value() {
            return this.value;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OnlineCourse getOnlineCourse() {
        return onlineCourse;
    }

    public void setOnlineCourse(OnlineCourse onlineCourse) {
        this.onlineCourse = onlineCourse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
