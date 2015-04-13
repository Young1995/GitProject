package org.svtcc.online.management.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.QuestionTypeDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.QuestionType;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.QuestionTypeService;
import org.svtcc.online.management.util.UserUtil;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {
	@Autowired
	private QuestionTypeDAO questionTypeDAO;

	@Override
	public void saveOrUpdate(QuestionType type) {
		if (type != null) {
			User user = UserUtil.getCurrentUser();
			if (type.getId() == null || type.getId() == 0) {
				// 添加
				type.setCreateDate(new Date());
				type.setCreateUser(user.getUsername());
			}

			type.setUpdateDate(new Date());
			type.setUpdateUser(user.getUsername());

			questionTypeDAO.saveOrUpdate(type);
		}

	}

	@Override
	public void deleteById(Integer id) {
		questionTypeDAO.deleteById(id);
	}

	@Override
	public PageSupport<QuestionType> findPagination(int pageNo, int pageSize) {
		return questionTypeDAO.findPagination(pageNo, pageSize);
	}

	@Override
	public PageSupport<QuestionType> findPaginationByType(Integer pageNo,
			Integer pageSize, Integer type) {
		// 参数检查
		if (pageNo == null) {
			pageNo = 1;
		}

		if (type == null) {
			type = 1;
		}

		if (pageSize == null) {
			pageSize = 10;
		}

		return questionTypeDAO.findPaginationByType(pageNo, pageSize, type);

	}

}
