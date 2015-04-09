package org.svtcc.online.management.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.CompanySettingDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.CompanySetting;
import org.svtcc.online.management.service.CompanySettingService;

@Service
public class CompanySettingServiceImpl implements CompanySettingService {
	private static final Log LOG = LogFactory.getLog(CompanySettingServiceImpl.class);
	
	@Autowired
	private CompanySettingDAO companySettingDAO;
	
	@Override
	public boolean addCompanySetting(CompanySetting setting) {
		try {
			companySettingDAO.saveOrUpdate(setting);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}
	
	// 更新CompanySetting
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.CompanySettingService#updateCompanySetting(org.svtcc.online.management.domain.CompanySetting)
	 */
	@Override
	public boolean updateCompanySetting(CompanySetting setting) {
		try {
			companySettingDAO.update(setting);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}
	
	// 获取编辑CompanySetting
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.CompanySettingService#getCompanySettingById(int)
	 */
	@Override
	public CompanySetting getCompanySettingById(int id) {
		return companySettingDAO.find(id);
	}
	
	// 删除CompanySetting
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.CompanySettingService#deleteById(int)
	 */
	@Override
	public void deleteById(int id) {
		companySettingDAO.deleteById(id);
	}
	// 查找所有CompanySetting
	public PageSupport<CompanySetting> findCompanySetting(int pageNo, int pageSize, int type) {
		return companySettingDAO.findPagination("from CompanySetting CompanySetting where CompanySetting.type=?", pageNo, pageSize, type);
	}

    @Override
    public List<CompanySetting> findAllCompanySettingsByType(int type) {
        return companySettingDAO.findAllCompanySettingsByType(type);
    }

    // 验证
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.CompanySettingService#validateCompanySetting(org.svtcc.online.management.domain.CompanySetting)
	 */
	@Override
	public Map<String, Object> validateCompanySetting(CompanySetting CompanySetting) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (StringUtils.isEmpty(CompanySetting.getSettingName())) {
			result.put("settingName", "名称不能为空");
		}

		if (StringUtils.isEmpty(CompanySetting.getDescription())) {
			if (CompanySetting.getType() == 1) {
				result.put("description", "名称不能为空");
			} else {
				result.put("description", "院系名称不能为空");
			}
		}

		return result;
	}
}
