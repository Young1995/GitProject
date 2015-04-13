package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.CompanySettingDAO;
import org.svtcc.online.management.domain.CompanySetting;

import java.util.List;

@Repository
public class CompanySettingDAOmpl extends BaseDaoImpl<CompanySetting> implements CompanySettingDAO{

    @Override
    public List<CompanySetting> findAllCompanySettingsByType(int type) {
        return findByProperty("type", type);
    }
}
