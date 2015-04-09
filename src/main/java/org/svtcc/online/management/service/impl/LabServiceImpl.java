package org.svtcc.online.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.LabDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Lab;
import org.svtcc.online.management.service.LabService;

@Service
public class LabServiceImpl implements LabService {

	@Autowired
	private LabDAO labDAO;

	/**
	 * Insert or update lab
	 */
	@Override
	public void saveOrUpdate(Lab t) {
		labDAO.saveOrUpdate(t);
	}
	
	/**
	 * Delete lab by id
	 */
	@Override
	public void deleteById(Integer id) {
		labDAO.deleteById(id);
	}

	/**
	 * find lab by pagination
	 */
	@Override
	public PageSupport<Lab> findPagination(int pageNo, int pageSize) {
		return labDAO.findPagination(pageNo, pageSize);
	}

}
