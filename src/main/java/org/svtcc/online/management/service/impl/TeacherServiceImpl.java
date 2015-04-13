package org.svtcc.online.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.constant.RoleConst;
import org.svtcc.online.management.dao.UserDao;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.TeacherService;

import java.util.List;

/**
 * Created  on 1/13/15.
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findByProperty("type", RoleConst.ROLE_TEACHER.getName());
    }
}
