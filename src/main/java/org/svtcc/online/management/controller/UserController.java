package org.svtcc.online.management.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.svtcc.online.management.constant.RoleConst;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.dto.ChangePasswordDTO;
import org.svtcc.online.management.dto.DepartmentDTO;
import org.svtcc.online.management.dto.UpdateProfileDTO;
import org.svtcc.online.management.dto.UploadFile;
import org.svtcc.online.management.exception.ChangePasswordException;
import org.svtcc.online.management.service.DepartmentService;
import org.svtcc.online.management.service.UserService;
import org.svtcc.online.management.util.FileUpLoadUtil;
import org.svtcc.online.management.util.FormErrorsUtil;
import org.svtcc.online.management.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 12/25/14.
 */
@Controller("/common_controller")
public class UserController {
	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentService departmentService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * BindingResult 必须跟在@Valid后面
	 * 
	 * @param user
	 * @param result
	 * @param role
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/common/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("user") User user,
			BindingResult result, String role,
			RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView("redirect:/register");

		LOG.debug("user register information: " + user.toString());

		// 验证用户输入
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				LOG.error(error.getDefaultMessage());
			}
			if (RoleConst.ROLE_STUDENT.getValue().equals(role)) {
				redirectAttributes.addFlashAttribute("errors",
						FormErrorsUtil.getErrorMap(result));
				redirectAttributes.addFlashAttribute("role", true);
			} else {
				redirectAttributes.addFlashAttribute("errors_teacher",
						FormErrorsUtil.getErrorMap(result));
				redirectAttributes.addFlashAttribute("role", true);
			}
			return view;
		}
		if (RoleConst.ROLE_STUDENT.getValue().equals(role)) {
			user.setType(RoleConst.ROLE_STUDENT.getName());
		} else {
			user.setType(RoleConst.ROLE_TEACHER.getName());
		}

		// 操作详情
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setCreateUser(user.getUsername());
		user.setUpdateUser(user.getUsername());
		
		if (userService.registerUser(user, role)) {
			// 注册成功
			redirectAttributes.addFlashAttribute("status", true);
			view.addObject("username", user.getUsername());
			view.setViewName("reg_success");
			return view;
		} else {
			// 注册失败
			redirectAttributes.addFlashAttribute("failed", true);
			return view;
		}
	}

	@RequestMapping(value = "/common/checkusername", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUserName(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		} else {
			return userService.isUserExist(username);
		}
	}

	@RequestMapping(value = "/user/getProfile", method = RequestMethod.GET)
	public ModelAndView redirect2UpdateProfilePage(boolean error) {
		ModelAndView view = new ModelAndView("userSetting");
		if (!error) {
			User user = UserUtil.getCurrentUser();

			view.addObject("user", user);
		}

		return view;
	}

	@RequestMapping(value = "/user/updateProfile", method = RequestMethod.POST)
	public ModelAndView updateProfile(@Valid UpdateProfileDTO updateProfile,
			BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(
				"redirect:/user/getProfile?error=true");
		User user = UserUtil.getCurrentUser();

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors",
					FormErrorsUtil.getErrorMap(result));
			redirectAttributes.addFlashAttribute("user", updateProfile);
			return view;
		}
		// TODO exception
		try {
			BeanUtils.copyProperties(user, updateProfile);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// 操作详情
		user.setUpdateDate(new Date());
		user.setUpdateUser(user.getUsername());
				
		User updatedUser = userService.update(user);
		UserUtil.refreshCurrentUser(updatedUser);
		return new ModelAndView("redirect:/user/getProfile");
	}

	/**
	 * 修改当前用户密码
	 * 
	 * @param changePasswordDTO
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
	public ModelAndView changePassword(
			@Valid ChangePasswordDTO changePasswordDTO, BindingResult result,
			RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView("redirect:/user/change_password");
		// 验证用户输入
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors",
					FormErrorsUtil.getErrorMap(result));
			return view;
		}

		// 检查新密码两次是否输入正确
		if (!changePasswordDTO.getNewPassword().equals(
				changePasswordDTO.getRePassword())) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("rePassword", "确认新密码不一致");
			redirectAttributes.addFlashAttribute("errors", errors);
			return view;
		}

		// 检查旧密码是否正确 写到service里面去
		User user = UserUtil.getCurrentUser();
		try {
			// 操作详情
			user.setUpdateDate(new Date());
			user.setUpdateUser(user.getUsername());
			
			boolean changeSuccess = userService.updatePassword(
					changePasswordDTO, user);
			if (!changeSuccess) {
				redirectAttributes.addFlashAttribute("error", "更改密码失败");
			} else {
				redirectAttributes.addFlashAttribute("error", "修改成功");
			}
		} catch (ChangePasswordException e) {
			LOG.warn(e.getMessage());
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("oldPassword", e.getMessage());
			redirectAttributes.addFlashAttribute("errors", errors);
		}

		return view;
	}

	@RequestMapping(value = "/load_department", method = RequestMethod.GET)
	@ResponseBody
	public List<DepartmentDTO> findAllDepartments() {
		return departmentService.findAllDepartments();
	}

	/**
	 * 上传用户头像
	 * 
	 * @param files
	 * @param uploadPath
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload_profile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadProfile(
			@RequestParam(value = "uploadImage") MultipartFile[] uploadImage,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String uploadPath = "upload/userprofile";
		FileUpLoadUtil fileUtil = new FileUpLoadUtil();

		List<UploadFile> uploadImages = fileUtil.uploadFile(uploadImage,
				uploadPath, request);
		if (uploadImages != null && uploadImages.size() == 1) {
			UploadFile uploadProfile = uploadImages.get(0);
			if (uploadProfile.isUploadSuccess()) {
				User currentUser = UserUtil.getCurrentUser();
				if(currentUser.getUserProfile() != null) {
					// 先删除之前的文件
					fileUtil.removeFile(request, currentUser.getUserProfile().getStoreFolder(), currentUser.getUserProfile().getRealName());
				}
				// 保存到数据库
				User user = userService.uploadCurrentUserProfile(uploadProfile,
						currentUser);

				// 更新当前用户的数据
				UserUtil.refreshCurrentUser(user);

				result.put("profileId", user.getUserProfile().getId());
				result.put("status", uploadProfile.isUploadSuccess());
				result.put("path", request.getContextPath() + "/" + uploadProfile.getStoreFolder() + "/"
						+ uploadProfile.getRealName());
				result.put("message", uploadProfile.getMessage());
				return result;
			}
		}

		result.put("status", false);
		result.put("msg", "上传头像失败!");
		return result;
	}

	/**
	 * 删除用户头像
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delete_profile", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteProfile(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		User currentUser = UserUtil.getCurrentUser();
		User user = userService.deleteCurrentUserProfile(currentUser);
		
		FileUpLoadUtil fileUtil = new FileUpLoadUtil();
		fileUtil.removeFile(request, currentUser.getUserProfile().getStoreFolder(), currentUser.getUserProfile().getRealName());
		
		if (user != null) {
			result.put("status", true);
		} else {
			result.put("status", false);
		}

		return result;
	}
}
