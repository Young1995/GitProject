package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.OnlineQuestionDAO;
import org.svtcc.online.management.domain.OnlineQuestion;

import java.util.List;

/**
 * Created by hanrenwei on 4/6/15.
 */
@Repository
public class OnlineQuestionDAOImpl extends BaseDaoImpl<OnlineQuestion> implements OnlineQuestionDAO {
    @Override
    public List<OnlineQuestion> findList(int pageNo, int pageSize, Integer userId) {
        String hql = "from OnlineQuestion onlineQuestion where onlineQuestion.userId=" + userId;
        return pagedQuery(hql, pageNo, pageSize);
    }
}
