package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.system.root.model.security.UserAccount;

public interface RoleUserInterfaceMenuBusiness extends TypedBusiness<RoleUserInterfaceMenu> {
    
	/**
	 * Find all UserInterfaceMenu associated to those roles
	 * @param roles
	 * @return collection of roles
	 */
	Collection<RoleUserInterfaceMenu> findByRoles(Collection<Role> roles);
	Collection<RoleUserInterfaceMenu> findByRole(Role role);
	
	Collection<RoleUserInterfaceMenu> findByUserAccount(UserAccount userAccount);
	
}
