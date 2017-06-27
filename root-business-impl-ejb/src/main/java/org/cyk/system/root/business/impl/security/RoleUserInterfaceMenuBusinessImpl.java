package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.RoleUserInterfaceMenuBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.RoleUserInterfaceMenuDao;

public class RoleUserInterfaceMenuBusinessImpl extends AbstractTypedBusinessService<RoleUserInterfaceMenu, RoleUserInterfaceMenuDao> implements RoleUserInterfaceMenuBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public RoleUserInterfaceMenuBusinessImpl(RoleUserInterfaceMenuDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<RoleUserInterfaceMenu> findByRoles(Collection<Role> roles) {
		return dao.readByRoles(roles);
	}
	
	@Override
	public Collection<RoleUserInterfaceMenu> findByRole(Role role) {
		return findByRoles(Arrays.asList(role));
	}
	
	@Override
	public Collection<RoleUserInterfaceMenu> findByUserAccount(UserAccount userAccount) {
		return findByRoles(userAccount.getRoles());
	}

}
