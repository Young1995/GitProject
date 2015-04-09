package org.svtcc.online.management.service;

import java.util.List;
import java.util.Map;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CompanySetting;

public interface CompanySettingService {

	// 添加CompanySetting
	public abstract boolean addCompanySetting(CompanySetting setting);

	// 更新CompanySetting
	public abstract boolean updateCompanySetting(CompanySetting setting);

	// 获取编辑CompanySetting
	public abstract CompanySetting getCompanySettingById(int id);

	// 删除CompanySetting
	public abstract void deleteById(int id);

	// 验证
	public abstract Map<String, Object> validateCompanySetting(CompanySetting setting);
	
	public PageSupport<CompanySetting> findCompanySetting(int pageNo, int pageSize, int type);

    List<CompanySetting> findAllCompanySettingsByType(int type);

}