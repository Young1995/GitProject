package org.svtcc.online.management.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.CourseAttendanceDAO;
import org.svtcc.online.management.dao.CourseTableInfoDAO;
import org.svtcc.online.management.dao.UserDao;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CourseAttendance;
import org.svtcc.online.management.domain.CourseTableInfo;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.CourseAttendanceService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class CourseAttendanceServiceImpl implements CourseAttendanceService {
	private static final Log logger = LogFactory
			.getLog(CourseAttendanceServiceImpl.class);

	@Autowired
	private CourseAttendanceDAO courseAttendanceDAO;

	@Autowired
	private CourseTableInfoDAO courseTableInfoDAO;

	@Autowired
	private UserDao userDao;

	@Override
	public void saveOrUpdate(CourseAttendance courseAttendance) {
		if (courseAttendance != null) {
			User user = UserUtil.getCurrentUser();
			if (courseAttendance.getId() == null
					|| courseAttendance.getId() == 0) {
				courseAttendance.setId(null);
				// 添加
				courseAttendance.setCreateDate(new Date());
				courseAttendance.setCreateUser(user.getUsername());
			}

			courseAttendance.setUpdateDate(new Date());
			courseAttendance.setUpdateUser(user.getUsername());

			// department

			courseAttendanceDAO.saveOrUpdate(courseAttendance);
		}
	}

	@Override
	public void deleteById(Integer id) {
		courseAttendanceDAO.deleteById(id);
	}

	@Override
	public PageSupport<CourseAttendance> findPagination(int pageNo, int pageSize) {
		return courseAttendanceDAO.findPagination(pageNo, pageSize);
	}

	// TODO: schedule task
	@Override
	public void generateAttendanceRecordForCheck() {
		// query all the course table information
		List<CourseTableInfo> allCourseTableInfos = courseTableInfoDAO
				.findAll();

		// for all teacher
		for (CourseTableInfo courseTableInfo : allCourseTableInfos) {
			CourseAttendance attendance = new CourseAttendance();
			attendance.setCourse(courseTableInfo);

			attendance.setCreateDate(new Date());
			attendance.setCreateUser("SYSTEM");

			attendance.setUpdateDate(new Date());
			attendance.setUpdateUser("SYSTEM");

			attendance.setCheckRole(1);

			courseAttendanceDAO.saveOrUpdate(attendance);
		}

		// for all class, which role is class monitor.
		for (CourseTableInfo courseTableInfo : allCourseTableInfos) {
			CourseAttendance attendance = new CourseAttendance();
			attendance.setCourse(courseTableInfo);

			attendance.setCreateDate(new Date());
			attendance.setCreateUser("SYSTEM");

			attendance.setUpdateDate(new Date());
			attendance.setUpdateUser("SYSTEM");

			attendance.setCheckRole(2);

			courseAttendanceDAO.saveOrUpdate(attendance);
		}

		// for all student, which role is student.

		// TODO: ensure that we use the correct student code.
		List<User> students = userDao.findAllUsersByRole("ROLE_STUDENT");
		for (User user : students) {
			for (CourseTableInfo courseTableInfo : allCourseTableInfos) {
				if (courseTableInfo.getClassId() == user.getClazz().getId()) {
					CourseAttendance attendance = new CourseAttendance();
					attendance.setCourse(courseTableInfo);

					attendance.setCreateDate(new Date());
					attendance.setCreateUser("SYSTEM");

					attendance.setUpdateDate(new Date());
					attendance.setUpdateUser("SYSTEM");

					attendance.setCheckRole(3);

					courseAttendanceDAO.saveOrUpdate(attendance);
				}
			}
		}
	}

	/**
	 * find attendace for teacher
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageSupport<CourseAttendance> findCourseAttendance(Integer pageNo,
			Integer pageSize, Integer checkRole) {
		User user = UserUtil.getCurrentUser();

		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}

		// teacher or class monitor
		return courseAttendanceDAO.findAttendance(pageNo, pageSize,
				user.getId(), checkRole);
	}

	@Override
	public CourseAttendance findCourseAttendanceById(Integer id) {
		return courseAttendanceDAO.find(id);
	}

	@Override
	public boolean checkAttendance(CourseAttendance attend) {
		try {
			User user = UserUtil.getCurrentUser();

			CourseAttendance originAttendance = findCourseAttendanceById(attend
					.getId());

			originAttendance.setChecked(true);
			originAttendance.setCheckUserId(user.getId());
			originAttendance.setCheckUserName(user.getName());
			originAttendance.setFinishDate(new Date());

			originAttendance.setUpdateDate(new Date());
			originAttendance.setUpdateUser(user.getName());

			// update data
			originAttendance.setAbsentStudent(attend.getAbsentStudent());
			originAttendance.setActualStudent(attend.getActualStudent());
			originAttendance.setBeginTime(attend.getBeginTime());
			originAttendance.setEndTime(attend.getEndTime());
			originAttendance.setTotalStudent(attend.getTotalStudent());

			courseAttendanceDAO.saveOrUpdate(originAttendance);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public PageSupport<CourseAttendance> findCourseAttendanceStatistic(
			Integer pageNo, Integer pageSize) {

		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}

		// teacher or class monitor
		return courseAttendanceDAO.findAttendance(pageNo, pageSize, 1);
	}
}
