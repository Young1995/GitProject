package org.svtcc.online.management.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.AttendanceSettingDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.AttendanceSetting;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.AttendanceSettingService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class AttendanceSettingServiceImpl implements AttendanceSettingService {

	@Autowired
	private AttendanceSettingDAO attendanceSettingDAO;

	@Override
	public void saveOrUpdate(AttendanceSetting t) {
		if (t != null) {
			User user = UserUtil.getCurrentUser();

			if (t.getId() == null || t.getId() == 0) {
				t.setCreateDate(new Date());
				t.setCreateUser(user.getName());
				t.setId(null);
				t.setUpdateDate(new Date());
				t.setUpdateUser(user.getName());
				attendanceSettingDAO.saveOrUpdate(t);
			} else {
				AttendanceSetting origin = attendanceSettingDAO.find(t.getId());
				origin.setDisplayAbsentStudent(t.isDisplayAbsentStudent());
				origin.setDisplayActualStudent(t.isDisplayActualStudent());
				origin.setDisplayBeginTime(t.isDisplayBeginTime());
				origin.setDisplayClassRoom(t.isDisplayClassRoom());
				origin.setDisplayCourseName(t.isDisplayCourseName());
				origin.setDisplayEndTime(t.isDisplayEndTime());
				origin.setDisplayLateStudent(t.isDisplayLateStudent());
				origin.setDisplayTotalStudent(t.isDisplayTotalStudent());
				
				origin.setUpdateDate(new Date());
				origin.setUpdateUser(user.getName());
				
				attendanceSettingDAO.saveOrUpdate(origin);
			}

			

			
		}
	}

	@Override
	public void deleteById(Integer id) {
		attendanceSettingDAO.deleteById(id);

	}

	@Override
	public PageSupport<AttendanceSetting> findPagination(int pageNo,
			int pageSize) {
		return attendanceSettingDAO.findPagination(pageNo, pageSize);
	}

	@Override
	public AttendanceSetting findAttendanceSettingByType(Integer type) {
		List<AttendanceSetting> settings = attendanceSettingDAO.findByProperty(
				"type", type);
		if (settings != null && settings.size() == 1) {
			return settings.get(0);
		} else {
			return new AttendanceSetting();
		}
	}

}
