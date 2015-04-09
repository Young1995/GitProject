package org.svtcc.online.management.dao;

import org.svtcc.online.management.domain.OnlineQuestion;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
public interface OnlineQuestionDAO extends BaseDAO<OnlineQuestion> {

    List<OnlineQuestion> findList(int pageNo, int pageSize, Integer userId);

}
