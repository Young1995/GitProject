package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.dto.OnlineQuestionDTO;

/**
 * Created by hanrenwei on 4/6/15.
 */
public interface OnlineQuestionService extends BaseService<OnlineQuestionDTO> {

    PageSupport<OnlineQuestionDTO> findPagination(int pageNo, int pageSize, Integer userId);

    void addComment(Integer noteId, String comment);

    OnlineQuestionDTO findById(Integer noteId);
}
