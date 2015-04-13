package org.svtcc.online.management.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created  on 1/11/15.
 */
public class MajorDTO {
    private Integer id;
    @NotEmpty(message = "专业名称不能为空")
    private String name;
    @NotNull(message = "院系Id不能为空")
    private Integer departmentId;

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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
