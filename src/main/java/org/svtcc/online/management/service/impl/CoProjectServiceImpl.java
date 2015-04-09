package org.svtcc.online.management.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.CoProjectDAO;
import org.svtcc.online.management.dao.CompanySettingDAO;
import org.svtcc.online.management.dao.DepartmentDAO;
import org.svtcc.online.management.dao.MajorDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CoProject;
import org.svtcc.online.management.domain.CompanySetting;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.CoProjectService;
import org.svtcc.online.management.util.DateUtil;
import org.svtcc.online.management.util.UserUtil;


@Service
public class CoProjectServiceImpl implements CoProjectService {
	private static Log logger = LogFactory.getLog(CoProjectServiceImpl.class);

	@Autowired
	private CoProjectDAO coProjectDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private MajorDAO majorDAO;

	@Autowired
	private CompanySettingDAO companySettingDAO;

	@Value("${per_page_size}")
	private int pageSize;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.svtcc.online.management.service.impl.CoProjectService#
	 * saveOrUpdateCoProject(org.svtcc.online.management.domain.CoProject,
	 * java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public boolean saveOrUpdateCoProject(CoProject coProject) {
		// prepare the objects
		// Department
		Department department = departmentDAO.findDepartmentById(coProject
				.getDepId());
		coProject.setDepartment(department);

		// major
		Major major = majorDAO.find(coProject.getMajorId());
		coProject.setMajor(major);

		// projectType
		CompanySetting projectType = companySettingDAO.find(coProject
				.getProjectTypeId());
		coProject.setProjectType(projectType);

		// coCompany
		CompanySetting coCompany = companySettingDAO.find(coProject
				.getCoCompanyId());
		coProject.setCoCompany(coCompany);

		User user = UserUtil.getCurrentUser();
		// save or update
		if (coProject.getId() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("保存合作项目");
			}
			// save
			coProject.setId(null);

			// operation details
			coProject.setCreateDate(new Date());
			coProject.setCreateUser(user.getUsername());
			coProject.setUpdateDate(new Date());
			coProject.setUpdateUser(user.getUsername());

			CoProject result = coProjectDAO.saveOrUpdate(coProject);
			return result != null;
		} else {
			// update
			if (logger.isDebugEnabled()) {
				logger.debug("更新合作项目");
			}
			// operation details
			coProject.setUpdateDate(new Date());
			coProject.setUpdateUser(user.getUsername());

			CoProject result = coProjectDAO.update(coProject);
			return result != null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.svtcc.online.management.service.impl.CoProjectService#deleteCoProject
	 * (java.lang.Integer)
	 */
	@Override
	public boolean deleteCoProject(Integer id) {
		try {
			coProjectDAO.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.svtcc.online.management.service.impl.CoProjectService#
	 * listCoProjectByStatus(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageSupport<CoProject> listCoProjectByStatus(Integer pageNo,
			Integer... status) {
		return coProjectDAO.findCoProjectByStatus(pageNo, pageSize, status);
	}

	@Override
	public CoProject findCoProjectById(Integer id) {
		return coProjectDAO.find(id);
	}

	@Override
	public boolean updateByStatus(Integer id, Integer status) {
		CoProject coProject = findCoProjectById(id);
		if (coProject != null) {
			User user = UserUtil.getCurrentUser();
			// operation details
			coProject.setUpdateDate(new Date());
			coProject.setUpdateUser(user.getUsername());

			coProject.setStatus(status);
			coProjectDAO.update(coProject);
			return true;
		}
		return false;
	}

	/**
	 * scan all the CoProject with status of 3, then judge the terminal date
	 */
	public void reSignCoProject() {
		List<CoProject> projects = coProjectDAO.findByProperty("status", 3);
		for (CoProject coProject : projects) {
			if (coProject.getTerminalDate().before(new Date())) {
				// change to terminal
				User user = UserUtil.getCurrentUser();
				// operation details
				coProject.setUpdateDate(new Date());
				coProject.setUpdateUser(user.getUsername());
				coProject.setStatus(4);

				coProjectDAO.update(coProject);

				// if auto sign, re-create a new record.
				if (coProject.isAutoSign()) {
					CoProject newProject = coProject;
					newProject.setId(null);
					// handle the start and terminal date
					newProject.setStartDate(DateUtils.addDays(
							coProject.getStartDate(), 1));
					newProject.setTerminalDate(DateUtils.addDays(coProject
							.getTerminalDate(), DateUtil.daysBetween(
							coProject.getTerminalDate(),
							coProject.getStartDate())));
					newProject.setStatus(3);

					// operation details
					coProject.setCreateDate(new Date());
					coProject.setCreateUser(user.getUsername());
					coProject.setUpdateDate(new Date());
					coProject.setUpdateUser(user.getUsername());

					coProjectDAO.insert(newProject);
				}
			}
		}
	}

}
