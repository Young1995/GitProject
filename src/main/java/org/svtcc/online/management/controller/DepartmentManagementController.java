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
import org.svtcc.online.management.dto.DepartmentDTO;
import org.svtcc.online.management.service.DepartmentService;
import org.svtcc.online.management.service.TeacherService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/department")
@Controller
public class DepartmentManagementController {
    private static final Log LOG = LogFactory.getLog(RoleController.class);

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView findAllDepartments() {
        ModelAndView view = new ModelAndView("department/list");
        view.addObject("departments", departmentService.findAllDepartments());
        view.addObject("teachers", teacherService.findAll());
        return view;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findByDepartmentId(Integer dptId) {
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("data", departmentService.findById(dptId));
        results.put("status", true);
        results.put("msg", "操作成功");
        return results;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDepartment(
            @Valid DepartmentDTO addDepartment, BindingResult result) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (result.hasErrors()) {
            results.put("status", false);
            results.put("msg", "操作失败");
            results.put("errors", result.getAllErrors());

            if (LOG.isDebugEnabled()) {
                LOG.debug("操作失败");
            }
            return results;
        }

        departmentService.addDepartments(addDepartment);
        results.put("status", true);
        results.put("msg", "操作成功");
        return results;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDepartment(
            @Valid DepartmentDTO addDepartment, BindingResult result) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (result.hasErrors()) {
            results.put("errors", result.getAllErrors());
            results.put("status", false);
            results.put("msg", "操作失败");
            return results;
        }

        departmentService.saveOrUpdate(addDepartment);
        results.put("status", true);
        results.put("msg", "操作成功");
        return results;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(Integer id) {
        ModelAndView view = new ModelAndView("redirect:/department/list");
        departmentService.deleteById(id);
        return view;
    }
}
