package org.svtcc.online.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.svtcc.online.management.domain.StudentOnlineCourse;
import org.svtcc.online.management.dto.SearchOnlineCourseDTO;
import org.svtcc.online.management.service.DepartmentService;
import org.svtcc.online.management.service.MajorService;
import org.svtcc.online.management.service.StudentOnlineCourseService;
import org.svtcc.online.management.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanrenwei on 3/18/15.
 */
@RequestMapping("/online/course")
@Controller
public class StudentOnlineCourseManagementController {
    @Autowired
    private StudentOnlineCourseService studentOnlineCourseService;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MajorService majorService;


    @RequestMapping(value = {"/choose_list"}, method = RequestMethod.GET)
    public ModelAndView findList(Integer pageNo, SearchOnlineCourseDTO searchOnlineCourseDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        String uri = request.getServletPath();
        ModelAndView view = new ModelAndView(uri);

        Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;

        int currentUserId = UserUtil.getCurrentUser().getId();

        view.addObject("courses", studentOnlineCourseService.findPagination(currentPage, 10, currentUserId, searchOnlineCourseDTO.getName(), searchOnlineCourseDTO.getDepartments(),
                searchOnlineCourseDTO.getMajors()));
        view.addObject("departments", departmentService.findAllDepartments());
        view.addObject("majors", majorService.findAll());
        return view;
    }

    @RequestMapping(value = {"/choose_approve_list", "/choose_approve_list/search"}, method = RequestMethod.GET)
    public ModelAndView chooseApproveList(Integer pageNo, SearchOnlineCourseDTO searchOnlineCourseDTO, HttpServletRequest request) throws UnsupportedEncodingException {
//        String uri = request.getServletPath();
        ModelAndView view = new ModelAndView("online/course/choose_approve_list");

        Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;
        int currentUserId = UserUtil.getCurrentUser().getId();
        view.addObject("courses", studentOnlineCourseService.findPagination(currentPage, 10, currentUserId, searchOnlineCourseDTO.getName(), searchOnlineCourseDTO.getStatus(), searchOnlineCourseDTO.getDepartments(),
                searchOnlineCourseDTO.getMajors()));
        view.addObject("departments", departmentService.findAllDepartments());
        view.addObject("majors", majorService.findAll());
        return view;
    }

    @RequestMapping(value = "/choose", method = RequestMethod.GET)
    public ModelAndView choose(Integer courseId) {
        ModelAndView view = new ModelAndView("redirect:/online/course/choose_list");
        studentOnlineCourseService.selectCourse(courseId, StudentOnlineCourse.Status.PENDING);
        return view;
    }

    @RequestMapping(value = "/choose/view", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> view(Integer id) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (null == id) {
            results.put("errors", "courseId is null");
            results.put("status", false);
            results.put("msg", "操作失败");
            return results;
        }
        results.put("status", true);
        results.put("msg", "操作成功");
        results.put("data", studentOnlineCourseService.findById(id));
        return results;
    }

    @RequestMapping(value = "/choose_approve_list/update_status", method = RequestMethod.POST)
    public ModelAndView updateStatus(Integer id, Integer status) {
        studentOnlineCourseService.updateStatus(id, StudentOnlineCourse.Status.valueOf(status));
        ModelAndView view = new ModelAndView("redirect:/online/course/choose_approve_list");
        return view;
    }
}
