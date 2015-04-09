package org.svtcc.online.management.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Role;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.dto.RegisterUserDTO;
import org.svtcc.online.management.dto.UploadFile;
import org.svtcc.online.management.service.RoleService;
import org.svtcc.online.management.service.UserService;
import org.svtcc.online.management.util.FileUpLoadUtil;
import org.svtcc.online.management.util.UserUtil;

@RequestMapping("/user_manage")
@Controller("User_Management_Controller")
public class UserManagementController {
	private static final Log LOG = LogFactory
			.getLog(UserManagementController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Resource
	private ReflectionSaltSource saltSource;

	@Resource
	private ShaPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAllUsers(Integer pageNo, String search) {
		ModelAndView view = new ModelAndView("user/list");
		if (LOG.isDebugEnabled()) {
			LOG.debug("find all the users, with parameters: " + pageNo + ", "
					+ search);
		}
		Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;
		view.addObject("users",
				userService.searchUsers(currentPage, 10, search));

		// 获取所有roles
		view.addObject("roles", roleService.findAllRoles());
		view.addObject("search", search == null ? "" : search);
		return view;
	}

	/**
	 * 添加或者修改用户信息
	 * 
	 * @param userDTO
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateUser(@Valid RegisterUserDTO userDTO,
			BindingResult result) {
		Map<String, Object> registerResult = new HashMap<String, Object>();

		if (result.hasErrors()) {
			if (userDTO.getId() == 0) {
				if (LOG.isDebugEnabled()) {
					for (FieldError error : result.getFieldErrors()) {
						LOG.debug(error.getDefaultMessage());
					}
				}
				registerResult.put("status", false);
				registerResult.put("msg", "请填写正确的信息");
				return registerResult;
			}
		}

		// 添加用户
		User user = new User();
		try {
			BeanUtils.copyProperties(user, userDTO);
			// 添加用户角色
			List<Role> userRoles = new ArrayList<Role>();
			if (userDTO.getRoles() != null) {
				for (Integer roleId : userDTO.getRoles()) {
					Role role = roleService.findRoleById(roleId);
					if (role != null) {
						userRoles.add(role);
					}
				}
				user.setRoles(userRoles);
			}
			if (userDTO.getId() == 0) {
				// 检查用户
				boolean isNotExist = userService.isUserExist(userDTO
						.getUsername());
				if (!isNotExist) {
					registerResult.put("status", false);
					registerResult.put("msg", "用户已存在");
					return registerResult;
				}
				// 保存user
				user.setId(null);

				// 操作详情
				User currentUser = UserUtil.getCurrentUser();
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				user.setCreateUser(currentUser.getUsername());
				user.setUpdateUser(currentUser.getUsername());

				boolean addResult = userService.saveUser(user);
				registerResult.put("status", addResult);
			} else {
				User originUser = userService.findUserById(userDTO.getId());
				originUser.setEmail(userDTO.getEmail());
				originUser.setName(userDTO.getName());
				originUser.setGender(userDTO.isGender());
				originUser.setPhoneNumber(userDTO.getPhoneNumber());
				originUser.setDepartment(userDTO.getDepartment());

				originUser.setRoles(userRoles);

				if (!StringUtils.isEmpty(userDTO.getPassword())) {
					originUser.setPassword(passwordEncoder.encodePassword(
							userDTO.getPassword(),
							saltSource.getSalt(originUser)));
				}
				// 操作详情
				User currentUser = UserUtil.getCurrentUser();
				user.setUpdateDate(new Date());
				user.setUpdateUser(currentUser.getUsername());

				userService.update(originUser);
				registerResult.put("status", true);
			}

			return registerResult;
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage());
		} catch (InvocationTargetException e) {
			LOG.error(e.getMessage());
		}

		registerResult.put("status", false);

		return registerResult;
	}

	/**
	 * 根据ID查询当前需要修改的user信息
	 * 
	 * @param id
	 * @return json对象
	 */
	@RequestMapping(value = "/preUpdate", method = RequestMethod.GET)
	@ResponseBody
	public User preUpdate(Integer id) {
		User user = userService.findUserById(id);
		return user;
	}

	/**
	 * 审核用户，包括禁用或启用
	 * 
	 * @param ids
	 * @param isVerified
	 *            false 禁用 ， true 启用
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyUsers(Integer[] ids, boolean isVerified) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (ids != null) {
			for (Integer id : ids) {
				User user = userService.findUserById(id);
				user.setEnabled(isVerified);

				// 更新用户
				userService.update(user);
			}

			result.put("status", true);
		} else {
			result.put("status", false);
		}

		return result;
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(Integer[] ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (ids != null) {
			for (Integer id : ids) {
				userService.deleteUserById(id);
			}
			result.put("status", true);
		} else {
			result.put("status", false);
		}

		return result;
	}

	@RequestMapping(value = "/list_user_by_type", method = RequestMethod.GET)
	public ModelAndView listTeacherByType(Integer pageNo, String type) {
		ModelAndView view = new ModelAndView("experts/company_list");
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("List users by %s, page number is: %d",
					type, pageNo));
		}
		Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;
		PageSupport<User> users = userService.searchUserByRole(currentPage, 10, type);
		view.addObject("users", users);
		if("ROLE_SCHOOL_EXPERT".equals(type)) {
			view.setViewName("experts/school_list");
		}

		return view;
	}

	// 管理兼职老师
	@RequestMapping(value = "/list_pt", method = RequestMethod.GET)
	public ModelAndView listPTTeachers(Integer pageNo) {
		ModelAndView view = new ModelAndView("experts/pt_teachers");
		if (LOG.isDebugEnabled()) {
			LOG.debug("find all the users, with parameters: " + pageNo);
		}
		Integer currentPage = (pageNo != null && pageNo != 0) ? pageNo : 1;
		String type = "兼职老师";
		view.addObject("users",
				userService.searchPTTeacher(currentPage, 10, type));

		return view;
	}

	@RequestMapping(value = "/register_pt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePTTeacher(@Valid User user,
			BindingResult result) {
		Map<String, Object> registerResult = new HashMap<String, Object>();

		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				for (FieldError error : result.getFieldErrors()) {
					LOG.debug(error.getDefaultMessage());
				}

			}
			registerResult.put("status", false);
			registerResult.put("msg", "请填写正确的信息");
			return registerResult;
		}

		// 添加用户
		try {
			// 添加用户角色
			// TODO: 角色若为空时，请提示先添加改角色，或者自动添加
			List<Role> userRoles = new ArrayList<Role>();
			Role role = roleService.findRoleByRoleName(user.getType());
			userRoles.add(role);
			user.setType(role.getRoleName());

			user.setRoles(userRoles);

			if (user.getId() == 0) {
				// 检查用户
				boolean isNotExist = userService
						.isUserExist(user.getUsername());
				if (!isNotExist) {
					registerResult.put("status", false);
					registerResult.put("msg", "用户已存在");
					return registerResult;
				}

				// 保存user
				user.setId(null);
				// 操作详情
				User currentUser = UserUtil.getCurrentUser();
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				user.setCreateUser(currentUser.getUsername());
				user.setUpdateUser(currentUser.getUsername());


				boolean addResult = userService.saveUser(user);
				registerResult.put("status", addResult);
			} else {
				// 用户如果未填写密码，则为原有密码
				User originUser = userService.findUserById(user.getId());
				originUser.setUsername(user.getUsername());
				originUser.setCompanyName(user.getCompanyName());
				originUser.setEmail(user.getEmail());
				originUser.setPhoneNumber(user.getPhoneNumber());
				originUser.setName(user.getName());
				originUser.setGender(user.getGender());
				originUser.setEnabled(user.isEnabled());

				// 操作详情
				User currentUser = UserUtil.getCurrentUser();
				user.setUpdateDate(new Date());
				user.setUpdateUser(currentUser.getUsername());
				
				userService.update(originUser);

				registerResult.put("status", true);
			}

			return registerResult;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		registerResult.put("status", false);

		return registerResult;
	}

	/**
	 * 删除单个用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pt_delete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		userService.deleteUserById(id);
		result.put("status", true);

		return result;
	}

	/**
	 * 批量上传用户
	 * 
	 * @param excelFile
	 *            excel文件
	 * @param roles
	 *            上传角色id
	 * @param department
	 *            上传角色部门
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload_users", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadUsersFromExcel(
			@RequestParam(value = "excelFile") MultipartFile[] excelFile,
			Integer[] roles, Integer department, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		// 上传文件
		String uploadPath = "upload/userinfo";
		FileUpLoadUtil fileUtil = new FileUpLoadUtil();
		fileUtil.setAllowTypes(".xls.xlsx");

		List<UploadFile> uploadedExcels = fileUtil.uploadFile(excelFile,
				uploadPath, request);

		// 处理上传数据
		if (uploadedExcels != null && uploadedExcels.size() == 1) {
			UploadFile uploadedExcel = uploadedExcels.get(0);
			if (uploadedExcel.isUploadSuccess()) {
				// 生成文件路劲
				String servletAbsolutePath = request.getSession()
						.getServletContext().getRealPath("/");
				String excelPath = servletAbsolutePath + uploadPath + "/"
						+ uploadedExcel.getRealName();

				// 读取并保存到数据库
				List<Integer> roleIds = new ArrayList<Integer>();
				if (roles != null && roles.length > 0) {
					for (Integer roleId : roles) {
						roleIds.add(roleId);
					}
				}
				boolean uploadedSuccess = userService.batchUploadUsers(
						excelPath, roleIds, department);
				if (uploadedSuccess) {
					result.put("status", true);
					result.put("msg", "用户添加成功！");
				} else {
					result.put("status", false);
					result.put("msg", "用户添加失败！");
				}
				// 如果存在文件上传成功，此时应该删除该文件
				fileUtil.removeFile(request, uploadedExcel.getStoreFolder(),
						uploadedExcel.getRealName());
			} else {
				result.put("status", false);
				result.put("msg", "文件上传失败！");
			}
		} else {
			result.put("status", false);
			result.put("msg", "文件上传失败！");
		}

		return result;
	}
}
