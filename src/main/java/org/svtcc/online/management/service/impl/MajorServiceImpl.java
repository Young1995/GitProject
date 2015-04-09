package org.svtcc.online.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.MajorDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.service.MajorService;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorDAO majorDAO;


    @Override
    public void saveOrUpdate(Major major) {
        majorDAO.saveOrUpdate(major);
    }


    @Override
    public void deleteById(Integer id) {
        majorDAO.deleteById(id);
    }

    @Override
    public PageSupport<Major> findPagination(int pageNo, int pageSize) {
        return majorDAO.findPagination(pageNo, pageSize);
    }

    @Override
    public List<Major> findAll() {
        return majorDAO.findAll();
    }

    @Override
    public PageSupport<Major> findByDepartment(int pageNo, int pageSize, String departmentName) {
        return majorDAO.findByDepartmentName(pageNo,pageSize,departmentName);
    }
}
