package org.svtcc.online.management.dao;

import org.svtcc.online.management.domain.OnlineNote;

import java.util.List;

/**
 * Created  on 1/11/15.
 */
public interface OnlineNoteDAO extends BaseDAO<OnlineNote> {

    List<OnlineNote> findList(int pageNo, int pageSize, Integer userId);

}
