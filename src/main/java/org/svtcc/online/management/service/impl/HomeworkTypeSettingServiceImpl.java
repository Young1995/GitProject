package org.svtcc.online.management.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.HomeworkTypeSettingDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.HomeworkTypeSetting;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.HomeworkTypeSettingService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class HomeworkTypeSettingServiceImpl implements
		HomeworkTypeSettingService {

	@Autowired
	private HomeworkTypeSettingDAO homeworkTypeSettingDAO;

	@Override
	public void saveOrUpdate(HomeworkTypeSetting t) {
		if (t != null) {
			User user = UserUtil.getCurrentUser();

			if (t.getId() == null || t.getId() == 0) {
				t.setCreateDate(new Date());
				t.setCreateUser(user.getName());
				t.setId(null);
				t.setUpdateDate(new Date());
				t.setUpdateUser(user.getName());
				homeworkTypeSettingDAO.saveOrUpdate(t);
			} else {
				HomeworkTypeSetting origin = homeworkTypeSettingDAO.find(t
						.getId());
				origin.setDescQuestion(t.isDescQuestion());
				origin.setFillQuestion(t.isFillQuestion());
				origin.setJudgeQuestion(t.isJudgeQuestion());
				origin.setMultiSelect(t.isMultiSelect());
				origin.setSingleSelect(t.isSingleSelect());

				origin.setUpdateDate(new Date());
				origin.setUpdateUser(user.getName());

				homeworkTypeSettingDAO.saveOrUpdate(origin);
			}
		}

	}

	@Override
	public void deleteById(Integer id) {
		homeworkTypeSettingDAO.deleteById(id);
	}

	@Override
	public PageSupport<HomeworkTypeSetting> findPagination(int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HomeworkTypeSetting findHomeworkTypeSetting() {
		List<HomeworkTypeSetting> homeworkTypeSettings = homeworkTypeSettingDAO
				.findAll();
		return homeworkTypeSettings != null && homeworkTypeSettings.size() > 0 ? homeworkTypeSettings
				.get(0) : new HomeworkTypeSetting();
	}

}
