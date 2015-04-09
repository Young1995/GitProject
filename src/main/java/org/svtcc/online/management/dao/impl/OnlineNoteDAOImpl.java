package org.svtcc.online.management.dao.impl;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.OnlineNoteDAO;
import org.svtcc.online.management.domain.OnlineNote;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
@Repository
public class OnlineNoteDAOImpl extends BaseDaoImpl<OnlineNote> implements OnlineNoteDAO {
    @Override
    public List<OnlineNote> findList(int pageNo, int pageSize, Integer userId) {
        String hql = "from OnlineNote onlineNote where onlineNote.userId=" + userId;
        return pagedQuery(hql, pageNo, pageSize);
    }

}
