package org.svtcc.online.management.service;

import org.svtcc.online.management.domain.User;

import java.util.List;

/**
 * Created  on 1/13/15.
 */
public interface TeacherService {
    List<User> findAll();
}
