package org.svtcc.online.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.ClassDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Clazz;
import org.svtcc.online.management.service.ClassService;

/**
 * Created  on 1/11/15.
 */
@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassDAO classDAO;


    @Override
    public void saveOrUpdate(Clazz clazz) {
        classDAO.saveOrUpdate(clazz);
    }

    @Override
    public void deleteById(Integer id) {
        classDAO.deleteById(id);
    }

    @Override
    public PageSupport<Clazz> findPagination(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PageSupport<Clazz> findByMajor(int pageNo, int pageSize, String major) {
        return classDAO.findByMajor(pageNo, pageSize, major);
    }
}
