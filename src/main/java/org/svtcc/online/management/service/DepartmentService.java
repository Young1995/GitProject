package org.svtcc.online.management.service;

import org.svtcc.online.management.dto.DepartmentDTO;

import java.util.List;

/**
 * Created  on 1/5/15.
 */
public interface DepartmentService extends BaseService<DepartmentDTO> {
    List<DepartmentDTO> findAllDepartments();

    void addDepartments(DepartmentDTO... departments);

    DepartmentDTO findById(Integer id);


}
