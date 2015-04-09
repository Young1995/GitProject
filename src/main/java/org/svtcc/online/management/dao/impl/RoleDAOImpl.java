package org.svtcc.online.management.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.Hibernate4DaoSupport;
import org.svtcc.online.management.dao.RoleDAO;
import org.svtcc.online.management.domain.Role;

import java.util.List;

@Repository(value = "roleDAO")
public class RoleDAOImpl extends Hibernate4DaoSupport implements RoleDAO {
	private static final Log LOG = LogFactory.getLog(RoleDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.svtcc.online.management.dao.impl.RoleDAO#findAllRoles()
	 */
	@Override
	public List<Role> findAllRoles() {
		return loadAll(Role.class);
	}

	@Override
	public void saveAll(Role... roles) {
		for (Role role : roles) {
			save(role);
		}
	}

	@Override
	public Role findRoleById(Integer id) {
		return get(Role.class, id);
	}

	@Override
	public void updateRole(Role role) {
		update(role);
	}

	@Override
	public Role findRoleByName(String name) {
		try {
			List<Role> roles = find(
					"from Role as role where role.roleCode = ?", name);
			if (roles == null || roles.size() != 1) {
				return null;
			} else {
				return roles.get(0);
			}
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public void deleteRole(Integer id) {
		delete(get(Role.class, id));
	}
}
