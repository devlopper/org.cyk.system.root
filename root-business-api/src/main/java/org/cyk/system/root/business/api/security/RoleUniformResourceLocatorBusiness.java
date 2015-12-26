package org.cyk.system.root.business.api.security;

import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.model.security.UserAccount;

public interface RoleUniformResourceLocatorBusiness extends TypedBusiness<RoleUniformResourceLocator> {
    
	/**
	 * Find all UniformResourceLocator associated to those roles
	 * @param roles
	 * @return collection of roles
	 */
	Collection<RoleUniformResourceLocator> findByRoles(Collection<Role> roles);
	
	/**
	 * Find the matching UniformResourceLocator associated to those roles
	 * @param url
	 * @param roles
	 * @return
	 */
	RoleUniformResourceLocator findByRoles(URL url,Collection<Role> roles);
	RoleUniformResourceLocator findByUserAccount(URL url,UserAccount userAccount);
	
	Collection<RoleUniformResourceLocator> findByUserAccount(UserAccount userAccount);
	
	Boolean isAccessible(URL url,Collection<Role> roles);
	Boolean isAccessibleByUserAccount(URL url,UserAccount userAccount);
}
