package org.svtcc.online.management.controller;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.svtcc.online.management.dao.OnlineCourseDAO;
import org.svtcc.online.management.domain.Document;
import org.svtcc.online.management.service.DocumentService;

import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanrenwei on 2/28/15.
 */
@RequestMapping("/online/course")
@Controller
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private OnlineCourseDAO onlineCourseDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/{courseId}/documents/add", method = RequestMethod.POST)
    public String add(@PathVariable("courseId")
                      Integer courseId,
                      @ModelAttribute("document") Document document,
                      @RequestParam("file") MultipartFile file) {

        Map<String, Object> results = new HashMap<String, Object>();
        System.out.println("Name:" + document.getName());
        System.out.println("Desc:" + document.getDescription());
        System.out.println("Link:" + document.getLink());
        System.out.println("File:" + file.getName());
        System.out.println("ContentType:" + file.getContentType());
        if (document.getType().equals(Document.TYPE.FILE.value())) {
            try {
                Blob blob = sessionFactory.getCurrentSession().getLobHelper().createBlob(file.getInputStream(), file.getSize());

                document.setFilename(file.getOriginalFilename());
                document.setContent(blob);
                document.setContentType(file.getContentType());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        results.put("status", "success");
        document.setOnlineCourse(onlineCourseDAO.find(courseId));
        documentService.saveOrUpdate(document);
        String type = "";
        int category = document.getCategory();
        if (category == Document.CATEGORY.INTRODUCTION.value()) {
            type = "introductions";
        } else if (category == Document.CATEGORY.PRINCIPAL.value()) {
            type = "principals";
        } else if (category == Document.CATEGORY.FEATURE.value()) {
            type = "features";
        } else if (category == Document.CATEGORY.SCHEME.value()) {
            type = "schemes";
        } else if (category == Document.CATEGORY.REFERENCE.value()) {
            type = "references";
        } else if (category == Document.CATEGORY.POINT.value()) {
            type = "points";
        }
        return "redirect:/online/course/" + courseId + "/" + type;
    }

    //    @RequestMapping("/download/{documentId}")
//    public String download(@PathVariable("documentId")
//                           Integer documentId, HttpServletResponse response) {
//
//        Document doc = documentService.get(documentId);
//        try {
//            response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
//            OutputStream out = response.getOutputStream();
//            response.setContentType(doc.getContentType());
//            IOUtils.copy(doc.getContent().getBinaryStream(), out);
//            out.flush();
//            out.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        return null;
//    }
//
    @RequestMapping("/{courseId}/documents/{type}/delete/{documentId}")
    public String remove(@PathVariable("courseId") Integer courseId,
                         @PathVariable("type") String type,
                         @PathVariable("documentId") Integer documentId) {
        documentService.deleteById(documentId);
        return "redirect:/online/course/" + courseId + "/" + type + "s";
    }
}
