package org.svtcc.online.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.DocumentDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Document;
import org.svtcc.online.management.service.DocumentService;

/**
 * Created  on 1/11/15.
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentDAO documentDAO;


    @Override
    public void saveOrUpdate(Document document) {
        documentDAO.saveOrUpdate(document);
    }

    @Override
    public void deleteById(Integer id) {
        documentDAO.deleteById(id);
    }

    @Override
    public PageSupport<Document> findPagination(int pageNo, int pageSize) {
        return documentDAO.findPagination(pageNo, pageSize);
    }

    @Override
    public PageSupport<Document> findPagination(int pageNo, int pageSize, int courseId, int category) {
        String hql = "from Document d where d.category = ? and d.onlineCourse.id= ? ";
        return documentDAO.findPagination(hql, pageNo, pageSize, category, courseId);
    }
}
