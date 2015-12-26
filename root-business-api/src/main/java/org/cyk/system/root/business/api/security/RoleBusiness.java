package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;

public interface RoleBusiness extends AbstractEnumerationBusiness<Role> {

	Role save(Role role, Collection<RoleUniformResourceLocator> roleUniformResourceLocators);
    
}
