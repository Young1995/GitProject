package org.svtcc.online.management.service;

import org.svtcc.online.management.domain.AttendanceSetting;

public interface AttendanceSettingService extends
		BaseService<AttendanceSetting> {
	
	public AttendanceSetting findAttendanceSettingByType(Integer type);

}
