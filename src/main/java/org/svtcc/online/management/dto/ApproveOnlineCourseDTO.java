package org.svtcc.online.management.dto;


/**
 * Created by hanrenwei on 1/18/15.
 */
public class ApproveOnlineCourseDTO {
    private Integer id;
    private Integer status;
    private String rejectReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
