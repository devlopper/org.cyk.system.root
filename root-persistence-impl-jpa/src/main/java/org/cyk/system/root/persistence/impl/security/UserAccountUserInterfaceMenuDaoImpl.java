package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;
import org.cyk.system.root.persistence.api.security.UserAccountUserInterfaceMenuDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class UserAccountUserInterfaceMenuDaoImpl extends AbstractTypedDao<UserAccountUserInterfaceMenu> implements UserAccountUserInterfaceMenuDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByUserAccounts;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByUserAccounts, _select().whereIdentifierIn(UserAccountUserInterfaceMenu.FIELD_USER_ACCOUNT));
	}
	
	@Override
	public Collection<UserAccountUserInterfaceMenu> readByUserAccounts(Collection<UserAccount> userAccounts) {
		return namedQuery(readByUserAccounts).parameterIdentifiers(userAccounts).resultMany();
	}
	
}
 