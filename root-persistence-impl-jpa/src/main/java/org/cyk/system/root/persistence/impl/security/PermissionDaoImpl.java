package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.PermissionDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class PermissionDaoImpl extends AbstractEnumerationDaoImpl<Permission> implements PermissionDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByUsername,readByUserAccount,readByRoleId,readByRolesIds;
	
	@Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByUsername, "SELECT permission FROM Permission permission "
        		+ "WHERE EXISTS (SELECT role FROM Role role WHERE permission MEMBER OF role.permissions AND "
        		+ "EXISTS (SELECT userAccount FROM UserAccount userAccount WHERE userAccount.credentials.username = :username AND role MEMBER OF userAccount.roles) )");
        
        registerNamedQuery(readByUserAccount, "SELECT permission FROM Permission permission "
        		+ "WHERE EXISTS (SELECT role FROM Role role WHERE permission MEMBER OF role.permissions AND "
        		+ "EXISTS (SELECT userAccount FROM UserAccount userAccount WHERE userAccount = :pUserAccount AND role MEMBER OF userAccount.roles) )");
        
        registerNamedQuery(readByRoleId, "SELECT permission FROM Permission permission "
        		+ "WHERE EXISTS (SELECT role FROM Role role WHERE role.identifier = :roleId AND permission MEMBER OF role.permissions)");
        
        registerNamedQuery(readByRolesIds, "SELECT permission FROM Permission permission "
        		+ "WHERE EXISTS (SELECT role FROM Role role WHERE role.identifier IN :rolesIds AND permission MEMBER OF role.permissions)");
    }
	
	@Override
	public Collection<Permission> readByUsername(String username) {
		return namedQuery(readByUsername).parameter("username", username).resultMany();
	}
	
	@Override
	public Collection<Permission> readByUserAccount(UserAccount anUserAccount) {
		return namedQuery(readByUserAccount).parameter("pUserAccount", anUserAccount).resultMany();
	}
	
	@Override
	public Collection<Permission> readByRoleId(Long roleId) {
		return namedQuery(readByRoleId).parameter("roleId", roleId).resultMany();
	}
	
	@Override
	public Collection<Permission> readByRolesIds(Set<Long> rolesIds) {
		return namedQuery(readByRolesIds).parameter("rolesIds", rolesIds).resultMany();
	}

}
 