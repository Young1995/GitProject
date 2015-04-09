package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CoProject;

public interface CoProjectService {

	/**
	 * save CoProject, pass the relate id and then query the object.
	 * 
	 * @param coProject
	 * @param depId
	 * @param majorId
	 * @param projectTypeId
	 * @param coCompanyId
	 * @return
	 */
	public abstract boolean saveOrUpdateCoProject(CoProject coProject);

	/**
	 * delete the CoProject by id
	 * 
	 * @param id
	 * @return
	 */
	public abstract boolean deleteCoProject(Integer id);

	/**
	 * find the CoProject pagination by status
	 * @param pageNo
	 * @param status
	 * @return
	 */
	public abstract PageSupport<CoProject> listCoProjectByStatus(
			Integer pageNo, Integer... status);
	
	/**
	 * find CoProject by id
	 * @param id
	 * @return
	 */
	public abstract CoProject findCoProjectById(Integer id);

	/**
	 * update by status and id
	 * @param id
	 * @param status
	 * @return
	 */
	public abstract boolean updateByStatus(Integer id, Integer status);
	
	public void reSignCoProject();
}