package org.cyk.system.root.persistence.api.security;

import java.util.Collection;

import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.system.root.persistence.api.TypedDao;

public interface RoleUserInterfaceMenuDao extends TypedDao<RoleUserInterfaceMenu> {

	Collection<RoleUserInterfaceMenu> readByRoles(Collection<Role> roles);
	
}
