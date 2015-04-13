package org.svtcc.online.management.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.LabLocationDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.LabLocation;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.LabLocationService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class LabLocationServiceImpl implements LabLocationService {

	@Autowired
	private LabLocationDAO labLocationDAO;

	@Override
	public void saveOrUpdate(LabLocation location) {
		if (location != null) {
			User user = UserUtil.getCurrentUser();
			if (location.getId() == null) {
				// 添加
				location.setCreateDate(new Date());
				location.setCreateUser(user.getUsername());
				location.setUpdateDate(new Date());
				location.setUpdateUser(user.getUsername());
			} else {
				// 更新
				location.setUpdateDate(new Date());
				location.setUpdateUser(user.getUsername());
			}

			labLocationDAO.saveOrUpdate(location);
		}
	}

	@Override
	public void deleteById(Integer id) {
		labLocationDAO.deleteById(id);
	}

	@Override
	public PageSupport<LabLocation> findPagination(int pageNo, int pageSize) {
		return labLocationDAO.findPagination(pageNo, pageSize);
	}

}
