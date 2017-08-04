package org.cyk.system.root.persistence.api.security;

import java.util.Collection;
import java.util.Map;

import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.persistence.api.TypedDao;

public interface RoleUniformResourceLocatorDao extends TypedDao<RoleUniformResourceLocator> {

	RoleUniformResourceLocator readByPathByParameters(String path,Map<String,String> parameters);
    
	Collection<RoleUniformResourceLocator> readByRoles(Collection<Role> roles);
	
}
