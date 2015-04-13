package org.svtcc.online.management.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.svtcc.online.management.domain.Role;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.service.RoleService;
import org.svtcc.online.management.util.UserUtil;

/**
 * 用户角色管理
 * 
 * @author troy
 * 
 */
@RequestMapping("/role")
@Controller
public class RoleController {
	private static final Log LOG = LogFactory.getLog(RoleController.class);

	@Autowired
	private RoleService roleService;

	/**
	 * 添加或修改用户角色
	 * 
	 * @param role
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/addOrEdit", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> addRole(@Valid Role role, BindingResult result) {
		Map<String, Object> results = new HashMap<String, Object>();
		if (result.hasErrors()) {
			results.put("status", false);
			results.put("msg", "操作失败，角色名或角色代码不能为空");
			return results;
		}

		if (role.getId() != 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("更新角色" + role.toString());
			}
			// 操作详情
			User user = UserUtil.getCurrentUser();
			role.setUpdateDate(new Date());
			role.setUpdateUser(user.getUsername());

			roleService.updateRole(role);
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("添加角色" + role.toString());
			}
			// 操作详情
			User user = UserUtil.getCurrentUser();
			role.setCreateDate(new Date());
			role.setUpdateDate(new Date());
			role.setCreateUser(user.getUsername());
			role.setUpdateUser(user.getUsername());
			
			roleService.addRoles(role);
		}
		results.put("status", true);
		results.put("msg", "操作成功");
		return results;
	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView loadAllRoles() {
		ModelAndView view = new ModelAndView("role/list");
		view.addObject("roles", roleService.findAllRoles());
		return view;
	}

	@RequestMapping(value = "/addOrEdit", method = RequestMethod.GET)
	public ModelAndView addOrEdit(Integer id) {
		ModelAndView view = new ModelAndView("role/edit");
		if (id == null || id == 0 || id == -1) {
			// 添加
			view.addObject("role", new Role());
		} else {
			view.addObject("role", roleService.findRoleById(id));
		}

		return view;
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(Integer id) {
		ModelAndView view = new ModelAndView("redirect:/role/list");
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("删除角色id=%d", id));
		}
		roleService.deleteRole(id);
		return view;
	}
}
