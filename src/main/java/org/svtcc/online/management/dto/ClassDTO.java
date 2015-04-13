package org.svtcc.online.management.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


/**
 * Created  on 1/16/15.
 */
public class ClassDTO {
    private Integer id;
    @NotEmpty(message = "班级名称不能为空")
    private String name;
    @NotNull(message = "专业不能为空")
    private Integer majorId;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
