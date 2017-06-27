package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;

public interface UserAccountUserInterfaceMenuBusiness extends TypedBusiness<UserAccountUserInterfaceMenu> {
    
	Collection<UserAccountUserInterfaceMenu> findByUserAccount(UserAccount userAccount);
	Collection<UserAccountUserInterfaceMenu> findByUserAccounts(Collection<UserAccount> userAccounts);
	
}
