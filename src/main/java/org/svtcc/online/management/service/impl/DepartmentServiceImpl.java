package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.DepartmentDAO;
import org.svtcc.online.management.dao.UserDao;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.dto.DepartmentDTO;
import org.svtcc.online.management.dto.MajorDTO;
import org.svtcc.online.management.service.DepartmentService;
import org.svtcc.online.management.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.transform;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDAO departmentDAO;
    @Autowired
    private UserDao userDao;

    @Override
    public List<DepartmentDTO> findAllDepartments() {

        Optional<List<Department>> departmentList = Optional.fromNullable(departmentDAO.findAllDepartments());
        if (departmentList.isPresent()) {
            return transform(departmentList.get(), new Function<Department, DepartmentDTO>() {
                public DepartmentDTO apply(Department department) {
                    DepartmentDTO departmentDTO = new DepartmentDTO();
                    departmentDTO.setId(department.getId());
                    departmentDTO.setName(department.getName());
                    departmentDTO.setDeanName(department.getDean().getName());
                    departmentDTO.setDeanId(department.getDean().getId());
                    Optional<List<Major>> majorList = Optional.fromNullable(department.getMajorList());
                    if (majorList.isPresent()) {
                        departmentDTO.setMajorList(transform(majorList.get(), new Function<Major, MajorDTO>() {
                            @Override
                            public MajorDTO apply(Major major) {
                                MajorDTO majorDTO = new MajorDTO();
//                                majorDTO.setDepartmentId(major.getDepartment().getId());
                                majorDTO.setId(major.getId());
                                majorDTO.setName(major.getName());
                                return majorDTO;
                            }
                        }));
                    }

                    return departmentDTO;
                }
            });
        }
        return new ArrayList<DepartmentDTO>();
    }

    @Override
    public void addDepartments(DepartmentDTO... departments) {
        User currentUser = UserUtil.getCurrentUser();
        List<Department> departmentList = new ArrayList<Department>();
        for (DepartmentDTO dpt : departments) {
            Department department = new Department();
            department.setName(dpt.getName());
            if (dpt.getDeanId() != null) {
                User user = userDao.findUserById(dpt.getDeanId());
                department.setDean(user);
            }
            // 操作详情
            department.setCreateDate(new Date());
            department.setUpdateDate(new Date());
            department.setCreateUser(currentUser.getUsername());
            department.setUpdateUser(currentUser.getUsername());

            departmentList.add(department);
        }
        departmentDAO.saveAll(departmentList
                .toArray(new Department[departments.length]));
    }

    @Override
    public DepartmentDTO findById(Integer id) {
        Department department = departmentDAO.findDepartmentById(id);
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setDeanId(department.getDean().getId());
        departmentDTO.setDeanName(department.getDean().getName());

        List<Major> majorList = department.getMajorList();
        List<MajorDTO> majorDTOList = new ArrayList<MajorDTO>();
        for (Major major : majorList) {
            MajorDTO majorDTO = new MajorDTO();
            majorDTO.setId(major.getId());
            majorDTO.setName(major.getName());
            majorDTOList.add(majorDTO);
        }
        departmentDTO.setMajorList(majorDTOList);
        return departmentDTO;
    }


    @Override
    public void saveOrUpdate(DepartmentDTO dpt) {
        User currentUser = UserUtil.getCurrentUser();
        Department department = new Department();
        department.setId(dpt.getId());
        department.setName(dpt.getName());
        if (dpt.getDeanId() != null) {
            User user = userDao.findUserById(dpt.getDeanId());
            department.setDean(user);
        }

        // 操作详情
        department.setUpdateDate(new Date());
        department.setUpdateUser(currentUser.getUsername());
        departmentDAO.updateDepartment(department);
    }

    @Override
    public void deleteById(Integer id) {
        departmentDAO.deleteDepartmentById(id);
    }

    @Override
    public PageSupport<DepartmentDTO> findPagination(int pageNo, int pageSize) {
        return null;
    }
}
