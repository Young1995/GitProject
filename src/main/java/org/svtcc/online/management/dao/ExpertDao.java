package org.svtcc.online.management.dao;

import org.svtcc.online.management.domain.Expert;

import java.util.List;

public interface ExpertDao extends BaseDAO<Expert>{
     List<Expert> findAllExpertsByType(int type);
}