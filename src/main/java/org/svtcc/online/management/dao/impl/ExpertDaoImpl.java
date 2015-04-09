package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.ExpertDao;
import org.svtcc.online.management.domain.Expert;

import java.util.List;

@Repository
public class ExpertDaoImpl extends BaseDaoImpl<Expert> implements ExpertDao {

    @Override
    public List<Expert> findAllExpertsByType(int type) {
        return findByProperty("type", type);
    }
}
