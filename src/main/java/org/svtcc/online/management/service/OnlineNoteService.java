package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.dto.OnlineNoteDTO;

/**
 * Created by hanrenwei on 4/1/15.
 */
public interface OnlineNoteService extends BaseService<OnlineNoteDTO> {
    PageSupport<OnlineNoteDTO> findPagination(int pageNo, int pageSize,Integer userId);
    void addComment(Integer noteId,String comment);

    OnlineNoteDTO findById(Integer noteId);
}
