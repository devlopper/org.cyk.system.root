package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.UserAccountUserInterfaceMenuBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;
import org.cyk.system.root.persistence.api.security.UserAccountUserInterfaceMenuDao;

public class UserAccountUserInterfaceMenuBusinessImpl extends AbstractTypedBusinessService<UserAccountUserInterfaceMenu, UserAccountUserInterfaceMenuDao> implements UserAccountUserInterfaceMenuBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserAccountUserInterfaceMenuBusinessImpl(UserAccountUserInterfaceMenuDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<UserAccountUserInterfaceMenu> findByUserAccounts(Collection<UserAccount> roles) {
		return dao.readByUserAccounts(roles);
	}
	
	@Override
	public Collection<UserAccountUserInterfaceMenu> findByUserAccount(UserAccount userAccount) {
		return findByUserAccounts(Arrays.asList(userAccount));
	}
	
}
