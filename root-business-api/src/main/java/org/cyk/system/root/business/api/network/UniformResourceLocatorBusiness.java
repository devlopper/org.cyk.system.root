package org.cyk.system.root.business.api.network;

import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;

public interface UniformResourceLocatorBusiness extends AbstractEnumerationBusiness<UniformResourceLocator> {
    
	void setFilteringEnabled(Boolean value);
	Boolean getFilteringEnabled();
	
	UniformResourceLocator find(URL url);
	UniformResourceLocator find(Collection<UniformResourceLocator> uniformResourceLocators,URL url);
	UniformResourceLocator findByRoles(Collection<Role> roles,URL url);
	UniformResourceLocator findByUserAccount(UserAccount userAccount,URL url);
	
	Boolean isAccessible(URL url);
	Boolean isAccessible(Collection<UniformResourceLocator> uniformResourceLocators,URL url);
	Boolean isAccessibleByRoles(Collection<Role> roles,URL url);
	Boolean isAccessibleByUserAccount(UserAccount userAccount,URL url);

}
