package org.cyk.system.root.persistence.api.security;

import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface PermissionDao extends AbstractEnumerationDao<Permission> {

	Collection<Permission> readByUsername(String username);
	
	Collection<Permission> readByUserAccount(UserAccount anUserAccount);
 
	Collection<Permission> readByRolesIds(Set<Long> rolesIds);
	
	Collection<Permission> readByRoleId(Long roleId);
}
