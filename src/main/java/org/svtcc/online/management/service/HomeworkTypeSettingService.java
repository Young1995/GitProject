package org.svtcc.online.management.service;

import org.svtcc.online.management.domain.HomeworkTypeSetting;

public interface HomeworkTypeSettingService extends
		BaseService<HomeworkTypeSetting> {
	public HomeworkTypeSetting findHomeworkTypeSetting();
}
