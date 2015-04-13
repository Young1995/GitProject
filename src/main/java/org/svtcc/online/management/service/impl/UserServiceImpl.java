package org.svtcc.online.management.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.constant.RoleConst;
import org.svtcc.online.management.dao.UserDao;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Role;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.domain.UserProfile;
import org.svtcc.online.management.dto.ChangePasswordDTO;
import org.svtcc.online.management.dto.UploadFile;
import org.svtcc.online.management.exception.ChangePasswordException;
import org.svtcc.online.management.service.RoleService;
import org.svtcc.online.management.service.UserService;
import org.svtcc.online.management.util.ExcelUtil;
import org.svtcc.online.management.util.UserUtil;

import javax.annotation.Resource;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Resource
	private ReflectionSaltSource saltSource;

	@Resource
	private ShaPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userDao.getUserByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("Can't find the username.");
		}

		LOG.debug(user.getUsername() + ": " + user.getPassword() + "| "
				+ user.isEnabled());

		return user;
	}

	public boolean registerUser(User user, String roleName) {
		try {
			Role role = roleService.findRoleByRoleName(roleName);
			if (role != null) {
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				user.setRoles(roles);
			}

			user.setType(RoleConst.valueOf(roleName).getValue());

			// set the password
			user.setSalt_value(System.currentTimeMillis() + "");
			user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
					saltSource.getSalt(user)));
			// 默认为false，需要等待审核
			user.setEnabled(true);

			userDao.saveOrUpdate(user);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 检查用户是否存在
	 * 
	 * @param username
	 * @return
	 */
	public boolean isUserExist(String username) {
		User user = userDao.getUserByUserName(username);

		if (user == null) {
			return true;
		}

		return false;
	}

	@Override
	public User update(User user) {
		return userDao.update(user);
	}

	@Override
	public User saveOrupdate(User user) {
		return userDao.saveOrUpdate(user);
	}

	/**
	 * 更新用户密码
	 * 
	 * @param changePasswordDTO
	 * @param user
	 * @return
	 * @throws ChangePasswordException
	 */
	public boolean updatePassword(ChangePasswordDTO changePasswordDTO, User user)
			throws ChangePasswordException {
		if (LOG.isDebugEnabled()) {
			LOG.debug(changePasswordDTO.toString());
		}
		// 检查旧密码是否正确
		String oldPassword = passwordEncoder.encodePassword(
				changePasswordDTO.getOldPassword(), saltSource.getSalt(user));
		if (!oldPassword.equals(user.getPassword())) {
			throw new ChangePasswordException("旧密码不正确");
		}

		// 修改密码
		String newPassword = passwordEncoder.encodePassword(
				changePasswordDTO.getNewPassword(), saltSource.getSalt(user));

		user.setPassword(newPassword);
		return userDao.updateUser(user);
	}

	@Override
	public PageSupport<User> findUsers(int pageNo, int pageSize, String type) {
		return userDao.listUsers(pageNo, pageSize, type);
	}

	@Override
	public boolean saveUser(User user) {
		user.setSalt_value(System.currentTimeMillis() + "");
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
				saltSource.getSalt(user)));
		return userDao.addUser(user);
	}

	@Override
	public User findUserById(Integer id) {
		return userDao.findUserById(id);
	}

	@Override
	public void deleteUserById(Integer id) {
		userDao.deleteById(id);
	}

	@Override
	public PageSupport<User> searchUsers(int pageNo, int pageSize, String search) {
		return userDao.searchUsers(pageNo, pageSize, search);
	}

	@Override
	public PageSupport<User> searchPTTeacher(int pageNo, int pageSize,
			String type) {
		return userDao.listUsers(pageNo, pageSize, type);
	}

	@Override
	public PageSupport<User> searchUserByRole(int pageNo, int pageSize, String roleCode) {
		return userDao.listUsersbyRole(pageNo, pageSize, roleCode);
	}
	/**
	 * 上传用户头像
	 */
	@Override
	public User uploadCurrentUserProfile(UploadFile uploadImage,
			User currentUser) {
		// 先清空改用户的之前的照片
		deleteCurrentUserProfile(currentUser);
		User user = userDao.find(currentUser.getId());

		UserProfile profile = new UserProfile();
		profile.setStoreFolder(uploadImage.getStoreFolder());
		profile.setOriginName(uploadImage.getOriginName());
		profile.setRealName(uploadImage.getRealName());
		profile.setType(uploadImage.getType());
		profile.setSize(uploadImage.getSize());
		
		// 操作详情
		profile.setCreateDate(new Date());
		profile.setUpdateDate(new Date());
		profile.setCreateUser(user.getUsername());
		profile.setUpdateUser(user.getUsername());

		user.setUserProfile(profile);

		return userDao.update(user);
	}

	/**
	 * 删除上传的头像
	 */
	public User deleteCurrentUserProfile(User currentUser) {
		User user = userDao.find(currentUser.getId());
		user.setUserProfile(null);
		// 更新当前用户数据
		UserUtil.refreshCurrentUser(user);

		return userDao.update(user);
	}

	/**
	 * 从excel中批量添加用户
	 * 
	 * @param excelPath
	 * @param roleIds
	 * @return
	 */
	public boolean batchUploadUsers(String excelPath, List<Integer> roleIds,
			Integer departmentId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("从%s批量添加用户", excelPath));
		}
		try {
			List<User> loadedUsers = extractUsersFromExcel(excelPath, roleIds,
					departmentId);
			System.out.println(loadedUsers.size());
			for (User user : loadedUsers) {
				 userDao.addUser(user);
			}
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 从excel中读取用户数据
	 * @param excelPath
	 * @param roleIds
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	private List<User> extractUsersFromExcel(String excelPath,
			List<Integer> roleIds, Integer departmentId) throws Exception {
		List<User> loadedUsers = new ArrayList<User>();

		// 准备role数据
		List<Role> roles = new ArrayList<Role>();
		for (Integer roleId : roleIds) {
			Role role = roleService.findRoleById(roleId);
			roles.add(role);
		}

		// 读取excel
		ExcelUtil excelUtil = new ExcelUtil();
		Iterator<Row> rows = excelUtil.readExcel(excelPath);
		while (rows.hasNext()) {
			Row row = rows.next();
			if (row.getLastCellNum() != 5) {
				// 不符合要求的列
				continue;
			}
			if (row.getRowNum() == 0) {
				// 第一行是标题
				continue;
			} else {
				User user = new User();

				// 0
				user.setUsername(ExcelUtil.getCellValue(row, 0).toString());
				// 1
				user.setName(ExcelUtil.getCellValue(row, 1).toString());
				// 性别
				// 2
				String gender = ExcelUtil.getCellValue(row, 2).toString();
				if ("1".equals(gender) || "男".equals(gender)) {
					user.setGender(true);
				} else {
					user.setGender(false);
				}

				// 3
				user.setEmail(ExcelUtil.getCellValue(row, 3).toString());

				// 4
				user.setPhoneNumber(ExcelUtil.getCellValue(row, 4).toString());

				user.setDepartment(departmentId);
				user.setRoles(roles);
				
				// default password
				user.setSalt_value(System.currentTimeMillis() + "");
				user.setPassword(passwordEncoder.encodePassword("123456", saltSource
						.getSalt(user)));

				loadedUsers.add(user);
			}
		}

		return loadedUsers;
	}
}
