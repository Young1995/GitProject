package org.svtcc.online.management.service;


import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Document;

/**
 * Created  on 1/15/15.
 */
public interface DocumentService extends BaseService<Document> {
    PageSupport<Document> findPagination(int pageNo, int pageSize, int courseId,int category);
}
