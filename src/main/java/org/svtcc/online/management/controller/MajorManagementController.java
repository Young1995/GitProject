package org.svtcc.online.management.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.Major;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.dto.DepartmentDTO;
import org.svtcc.online.management.dto.MajorDTO;
import org.svtcc.online.management.service.DepartmentService;
import org.svtcc.online.management.service.MajorService;
import org.svtcc.online.management.util.UserUtil;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/major")
@Controller
public class MajorManagementController {
    private static final Log LOG = LogFactory
            .getLog(MajorManagementController.class);
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MajorService majorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView findAllMajors(Integer pageNo, String search)
            throws UnsupportedEncodingException {
        ModelAndView view = new ModelAndView("major/list");
        if (search != null) {
            search = URLDecoder.decode(search, "UTF-8");
        }
        Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;
        majorService.findByDepartment(currentPage, 10, search);
        view.addObject("majors",
                majorService.findByDepartment(currentPage, 10, search));
        view.addObject("departments", departmentService.findAllDepartments());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addMajor(@Valid MajorDTO addMajor,
                                        BindingResult result) {
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
        Major major = convertToMajor(addMajor);

        // 操作详情
        User user = UserUtil.getCurrentUser();
        major.setCreateDate(new Date());
        major.setUpdateDate(new Date());
        major.setCreateUser(user.getUsername());
        major.setUpdateUser(user.getUsername());

        majorService.saveOrUpdate(major);
        results.put("status", true);
        results.put("msg", "操作成功");
        return results;
    }

    private Major convertToMajor(MajorDTO addMajor) {
        Major major = new Major();
        try {
            BeanUtils.copyProperties(major, addMajor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (addMajor.getDepartmentId() != null) {
            Department department = new Department();
            DepartmentDTO departmentDTO = departmentService.findById(addMajor
                    .getDepartmentId());
            department.setId(departmentDTO.getId());
            major.setDepartment(department);
        }
        return major;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDepartment(@Valid MajorDTO updateMajor,
                                                BindingResult result) {
        Map<String, Object> results = new HashMap<String, Object>();
        if (result.hasErrors()) {
            results.put("status", false);
            results.put("msg", "操作失败");
            results.put("errors", result.getAllErrors());
            return results;
        }
        Major major = convertToMajor(updateMajor);

        // 操作详情
        User user = UserUtil.getCurrentUser();
        major.setUpdateDate(new Date());
        major.setUpdateUser(user.getUsername());

        majorService.saveOrUpdate(major);
        results.put("status", true);
        results.put("msg", "操作成功");
        return results;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(Integer id) {
        ModelAndView view = new ModelAndView("redirect:/major/list");
        majorService.deleteById(id);
        return view;
    }
}
