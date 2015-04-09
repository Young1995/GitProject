package org.svtcc.online.management.service;

import java.util.List;

import org.svtcc.online.management.dto.DataDictDTO;

/**
 * 
 * @author Kevin
 *
 */
public interface DataDictService extends BaseService<DataDictDTO> {

	public List<DataDictDTO> getDataDictByGroupId(String groupId);
	
	public List<DataDictDTO> getDataDictByCode(String code);
	
	public List<DataDictDTO> getDataDictByName(String name);
}
