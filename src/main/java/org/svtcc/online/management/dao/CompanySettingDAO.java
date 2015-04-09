package org.svtcc.online.management.dao;

import org.svtcc.online.management.domain.CompanySetting;

import java.util.List;

public interface CompanySettingDAO extends BaseDAO<CompanySetting>{
    List<CompanySetting> findAllCompanySettingsByType(int type);
}