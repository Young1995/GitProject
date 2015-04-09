package org.svtcc.online.management.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.ExpertDao;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Expert;
import org.svtcc.online.management.service.ExpertService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpertServiceImpl implements ExpertService {
    private static final Log LOG = LogFactory.getLog(ExpertServiceImpl.class);
    @Autowired
    private ExpertDao expertDao;

    // 添加expert
    /* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.ExpertService#addExpert(org.svtcc.online.management.domain.Expert)
	 */
    @Override
    public boolean addExpert(Expert expert) {
        try {
            expertDao.saveOrUpdate(expert);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    // 更新expert
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.ExpertService#updateExpert(org.svtcc.online.management.domain.Expert)
	 */
    @Override
    public boolean updateExpert(Expert expert) {
        try {
            expertDao.update(expert);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    // 获取编辑expert
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.ExpertService#getExpertById(int)
	 */
    @Override
    public Expert getExpertById(int id) {
        return expertDao.find(id);
    }

    // 删除expert
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.ExpertService#deleteById(int)
	 */
    @Override
    public void deleteById(int id) {
        expertDao.deleteById(id);
    }

    // 查找所有expert
    public PageSupport<Expert> findExperts(int pageNo, int pageSize, int type) {
        return expertDao.findPagination("from Expert expert where expert.type=?", pageNo, pageSize, type);
    }

    @Override
    public List<Expert> findAllExpertsByType(int type) {
        return expertDao.findAllExpertsByType(type);
    }

    // 验证
	/* (non-Javadoc)
	 * @see org.svtcc.online.management.service.impl.ExpertService#validateExpert(org.svtcc.online.management.domain.Expert)
	 */
    @Override
    public Map<String, Object> validateExpert(Expert expert) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (StringUtils.isEmpty(expert.getExpertName())) {
            result.put("expertName", "专家名称不能为空");
        }

        if (expert.getType() == 1) {
			if (StringUtils.isEmpty(expert.getDepartmentName())) {
				result.put("departmentName", "企业名称不能为空");
			}
		} else {
			if (expert.getDeparmentId() == 0
					||StringUtils.isEmpty(expert.getDepartmentName())) {
				result.put("departmentName", "院系名称不能为空");
			}
		}
        return result;
    }
}
