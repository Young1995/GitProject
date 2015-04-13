package org.svtcc.online.management.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.svtcc.online.management.dto.CourseDTO;
import org.svtcc.online.management.service.CompanySettingService;
import org.svtcc.online.management.service.CourseService;
import org.svtcc.online.management.service.DepartmentService;
import org.svtcc.online.management.service.ExpertService;
import org.svtcc.online.management.util.FormErrorsUtil;

import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/course")
@Controller
public class CourseManagementController {
    private static final Log LOG = LogFactory.getLog(CourseManagementController.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private CompanySettingService companySettingService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView findList(Integer pageNo, String search) throws UnsupportedEncodingException {
        ModelAndView view = new ModelAndView("course/list");
        Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;
        if (search != null) {
            search = URLDecoder.decode(search, "UTF-8");
        }
        view.addObject("courses", courseService.findPagination(currentPage, 10));
        view.addObject("schoolExperts", expertService.findAllExpertsByType(2));
        view.addObject("companyExperts", expertService.findAllExpertsByType(1));
        view.addObject("coursesStandard", companySettingService.findAllCompanySettingsByType(2));
        view.addObject("teachingEffects", companySettingService.findAllCompanySettingsByType(3));
        view.addObject("departments", departmentService.findAllDepartments());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid CourseDTO addCourse, BindingResult result,
                            RedirectAttributes redirectAttributes) {
        Map<String, Object> results = new HashMap<String, Object>();
        ModelAndView view = new ModelAndView("redirect:/course/list");
        if (result.hasErrors()) {
            results.put("errors", result.getAllErrors());
            results.put("status", false);
            results.put("msg", "操作失败");
            if (LOG.isDebugEnabled()) {
                LOG.debug("操作失败");
            }
            redirectAttributes.addFlashAttribute("course",
                    addCourse);
            redirectAttributes.addFlashAttribute("errors",
                    FormErrorsUtil.getErrorMap(result));
            return view;
        } else {

            courseService.saveOrUpdate(addCourse);
            redirectAttributes.addFlashAttribute("status", true);
            return view;
        }

    }



    @RequestMapping(value = "/view", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> view(Integer courseId) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (null == courseId) {
            results.put("errors", "courseId is null");
            results.put("status", false);
            results.put("msg", "操作失败");
            return results;
        }
        results.put("status", true);
        results.put("msg", "操作成功");
        results.put("data", courseService.findById(courseId));
        return results;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> edit(Integer courseId) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (null == courseId) {
            results.put("errors", "courseId is null");
            results.put("status", false);
            results.put("msg", "操作失败");
            return results;
        }
        results.put("status", true);
        results.put("msg", "操作成功");
        results.put("course", courseService.findById(courseId));
        results.put("schoolExperts", expertService.findAllExpertsByType(2));
        results.put("companyExperts", expertService.findAllExpertsByType(1));
        results.put("coursesStandard", companySettingService.findAllCompanySettingsByType(2));
        results.put("teachingEffects", companySettingService.findAllCompanySettingsByType(3));
        results.put("departments", departmentService.findAllDepartments());
        return results;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@Valid CourseDTO updateCourse, BindingResult result) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (result.hasErrors()) {
            results.put("errors", result.getAllErrors());
            results.put("status", false);
            results.put("msg", "操作失败");
            return results;
        }
        courseService.saveOrUpdate(updateCourse);
        results.put("status", true);
        results.put("msg", "操作成功");
        return results;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(Integer id) {
        courseService.deleteById(id);
        ModelAndView view = new ModelAndView("redirect:/course/list");
        return view;
    }
}
