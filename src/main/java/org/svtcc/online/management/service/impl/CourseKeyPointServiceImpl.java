package org.svtcc.online.management.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.CourseKeyPointDAO;
import org.svtcc.online.management.dao.OnlineCourseDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseKeyPoint;
import org.svtcc.online.management.domain.OnlineCourse;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.CourseKeyPointService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class CourseKeyPointServiceImpl implements CourseKeyPointService {

	@Autowired
	private CourseKeyPointDAO courseKeyPointDAO;

	@Autowired
	private OnlineCourseDAO onlineCourseDAO;

	@Override
	public void saveOrUpdate(CourseKeyPoint t) {
		User user = UserUtil.getCurrentUser();

		if (t.getId() == null || t.getId() == 0) {
			t.setId(null);
			t.setCreateDate(new Date());
			t.setCreateUser(user.getName());
		}

		if (t.getCourseId() != null && t.getCourseId() != 0) {
			OnlineCourse course = onlineCourseDAO.find(t.getCourseId());
			if (course != null) {
				t.setCourse(course);
			}
		}
		
		t.setBelongUserId(user.getId());
		t.setUpdateDate(new Date());
		t.setUpdateUser(user.getName());

		courseKeyPointDAO.saveOrUpdate(t);

	}

	@Override
	public void deleteById(Integer id) {
		courseKeyPointDAO.deleteById(id);
	}

	@Override
	public PageSupport<CourseKeyPoint> findPagination(int pageNo, int pageSize) {
		return courseKeyPointDAO.findPagination(pageNo, pageSize);
	}
	
	@Override
	public CourseKeyPoint findCourseKeyPointById(Integer id) {
		return courseKeyPointDAO.find(id);
	}
	
	@Override
	public PageSupport<CourseKeyPoint> findPagination4CurrentUser(Integer pageNo, Integer pageSize) {
		User user = UserUtil.getCurrentUser();
		if(pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		
		if(pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		return courseKeyPointDAO.findPaginationByUserId(pageNo, pageSize, user.getId());
	}

}
