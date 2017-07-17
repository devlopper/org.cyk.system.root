package org.cyk.system.root.persistence.api.security;

import java.util.Collection;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;
import org.cyk.system.root.persistence.api.TypedDao;

public interface UserAccountUserInterfaceMenuDao extends TypedDao<UserAccountUserInterfaceMenu> {

	Collection<UserAccountUserInterfaceMenu> readByUserAccounts(Collection<UserAccount> userAccounts);
	
}
