package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Expert;

import java.util.List;
import java.util.Map;

public interface ExpertService {

    // 添加expert
    public abstract boolean addExpert(Expert expert);

    // 更新expert
    public abstract boolean updateExpert(Expert expert);

    // 获取编辑expert
    public abstract Expert getExpertById(int id);

    // 删除expert
    public abstract void deleteById(int id);

    // 验证
    public abstract Map<String, Object> validateExpert(Expert expert);

    public PageSupport<Expert> findExperts(int pageNo, int pageSize, int type);

    List<Expert> findAllExpertsByType(int type);

}