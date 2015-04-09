package org.svtcc.online.management.dto;


import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class DepartmentDTO {
    @NotEmpty(message = "院系名称不能为空")
    private String name;
    private String deanName;
    private Integer deanId;
    private Integer id;

    private List<MajorDTO> majorList;

    public List<MajorDTO> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<MajorDTO> majorList) {
        this.majorList = majorList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeanId() {
        return deanId;
    }

    public void setDeanId(Integer deanId) {
        this.deanId = deanId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }
}
