package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.DepartmentDAO;
import org.svtcc.online.management.dao.Hibernate4DaoSupport;
import org.svtcc.online.management.domain.Department;

import java.util.List;

@Repository
public class DepartmentDAOImpl extends Hibernate4DaoSupport implements DepartmentDAO {
    @Override
    public List<Department> findAllDepartments() {
        return loadAll(Department.class);
    }

    @Override
    public void saveAll(Department... departments) {
        for (Department dpt : departments) {
            save(dpt);
        }
    }

    @Override
    public Department findDepartmentById(Integer id) {
        return (Department)getCurrentSession().get(Department.class, id);
    }

    @Override
    public void updateDepartment(Department department) {
        getCurrentSession().merge(department);
    }

    @Override
    public Department findDepartmentByName(String name) {
        return null;
    }

    @Override
    public void deleteDepartmentById(Integer id) {
        delete(findDepartmentById(id));
    }
}
