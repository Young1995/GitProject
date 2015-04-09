package org.svtcc.online.management.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.InventoryDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Inventory;
import org.svtcc.online.management.domain.User;
import org.svtcc.online.management.util.UserUtil;

@Service
public class InventoryServiceImpl {
	private static final Log logger = LogFactory
			.getLog(InventoryServiceImpl.class);

	@Autowired
	private InventoryDAO inventoryDAO;

	/**
	 * 添加或修改
	 * 
	 * @param inventory
	 * @return
	 */
	public boolean saveOrUpdateInventory(Inventory inventory) {
		if (inventory != null) {
			User user = UserUtil.getCurrentUser();
			if (inventory.getId() == null) {
				// 添加
				inventory.setCreateDate(new Date());
				inventory.setCreateUser(user.getUsername());
				inventory.setUpdateDate(new Date());
				inventory.setUpdateUser(user.getUsername());
			} else {
				// 更新
				inventory.setUpdateDate(new Date());
				inventory.setUpdateUser(user.getUsername());
			}

			return null != inventoryDAO.saveOrUpdate(inventory);
		}

		return false;
	}

	/**
	 * 根据Id删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteInventory(Integer id) {
		try {
			inventoryDAO.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 分页查询
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public PageSupport<Inventory> findByPagination(Integer pageSize,
			Integer pageNo) {
		if (pageSize == null) {
			pageSize = 10;
		}

		if (pageNo == null) {
			pageNo = 1;
		}

		return inventoryDAO.findPagination(pageNo, pageSize);
	}

}
