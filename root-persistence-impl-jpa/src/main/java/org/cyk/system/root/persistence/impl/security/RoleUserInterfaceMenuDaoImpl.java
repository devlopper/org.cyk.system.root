package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.system.root.persistence.api.security.RoleUserInterfaceMenuDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class RoleUserInterfaceMenuDaoImpl extends AbstractTypedDao<RoleUserInterfaceMenu> implements RoleUserInterfaceMenuDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByRoles;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByRoles, _select().whereIdentifierIn(RoleUserInterfaceMenu.FIELD_ROLE));
	}
	
	@Override
	public Collection<RoleUserInterfaceMenu> readByRoles(Collection<Role> roles) {
		return namedQuery(readByRoles).parameterIdentifiers(roles).resultMany();
	}
	
}
 