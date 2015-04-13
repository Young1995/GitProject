package org.svtcc.online.management.dao;


import org.svtcc.online.management.domain.Department;

import java.util.List;

public interface DepartmentDAO {
    List<Department> findAllDepartments();

    void saveAll(Department... departments);

    Department findDepartmentById(Integer id);

    void updateDepartment(Department department);

    Department findDepartmentByName(String name);

    void deleteDepartmentById(Integer id);
}
