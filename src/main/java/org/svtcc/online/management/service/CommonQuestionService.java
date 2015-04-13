package org.svtcc.online.management.service;

import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.dto.CommonQuestionDTO;

/**
 * Created by hanrenwei on 4/6/15.
 */
public interface CommonQuestionService extends BaseService<CommonQuestionDTO> {

    PageSupport<CommonQuestionDTO> findPagination(int pageNo, int pageSize, Integer userId);

    void addComment(Integer noteId, String comment);

    CommonQuestionDTO findById(Integer noteId);
}
