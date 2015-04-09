package org.svtcc.online.management.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.CourseKeyPointDAO;
import org.svtcc.online.management.dao.HomeworkDAO;
import org.svtcc.online.management.dao.OnlineCourseDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseKeyPoint;
import org.svtcc.online.management.domain.Homework;
import org.svtcc.online.management.domain.OnlineCourse;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.HomeworkService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class HomeworkServiceImpl implements HomeworkService {

	@Autowired
	private HomeworkDAO homeworkDAO;

	@Autowired
	private OnlineCourseDAO onlineCourseDAO;

	@Autowired
	private CourseKeyPointDAO courseKeyPointDAO;

	@Override
	public void saveOrUpdate(Homework homework) {
		User user = UserUtil.getCurrentUser();
		if (homework.getId() == null || homework.getId() == 0) {
			homework.setId(null);
			homework.setCreateDate(new Date());
			homework.setCreateUser(user.getName());
		} else {
			// update
			Homework origin = homeworkDAO.find(homework.getId());
			homework.setCreateDate(origin.getCreateDate());
			homework.setCreateUser(origin.getCreateUser());
		}

		// add course
		OnlineCourse course = onlineCourseDAO.find(homework.getCourseId());
		if (course != null) {
			homework.setOnlineCourse(course);
		}

		// add course key point
		CourseKeyPoint keyPoint = courseKeyPointDAO.find(homework
				.getKeypointId());
		if (keyPoint != null) {
			homework.setKeypoint(keyPoint);
		}

		homework.setBelongUserId(user.getId());

		homework.setUpdateDate(new Date());
		homework.setUpdateUser(user.getName());

		homeworkDAO.saveOrUpdate(homework);
	}

	@Override
	public void deleteById(Integer id) {
		homeworkDAO.deleteById(id);
	}

	@Override
	public PageSupport<Homework> findPagination(int pageNo, int pageSize) {
		return homeworkDAO.findPagination(pageNo, pageSize);
	}

	@Override
	public Homework findById(Integer id) {
		return homeworkDAO.find(id);
	}

	@Override
	public PageSupport<Homework> findPagination4CurrentUser(Integer pageNo,
			Integer pageSize) {
		User user = UserUtil.getCurrentUser();
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize == 1) {
			pageSize = 10;
		}
		return homeworkDAO.findPaginationByUserId(pageNo, pageSize,
				user.getId());

	}

	@Override
	public boolean pubHomework(Integer homeworkId) {
		Homework homework = homeworkDAO.find(homeworkId);
		if (homework != null) {
			homework.setPublish(true);
			homeworkDAO.saveOrUpdate(homework);
			return true;
		}
		return false;
	}

	public PageSupport<Homework> findHomeworksForStudent(Integer pageNo,
			Integer pageSize) {
		User user = UserUtil.getCurrentUser();
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize == 1) {
			pageSize = 10;
		}

		return homeworkDAO.findHomeworkForStudent(user.getId(), pageNo,
				pageSize);
	}
}
