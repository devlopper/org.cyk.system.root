package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.system.root.persistence.api.security.RoleUniformResourceLocatorDao;

public class RoleBusinessImpl extends AbstractEnumerationBusinessImpl<Role, RoleDao> implements RoleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public RoleBusinessImpl(RoleDao dao) {
		super(dao); 
	}  

	@Override
	protected void afterCreate(Role role) {
		super.afterCreate(role);
		if(role.getRoleUniformResourceLocators().isSynchonizationEnabled())
			inject(RoleUniformResourceLocatorBusiness.class).create(role.getRoleUniformResourceLocators().getCollection());
	}
	
	@Override
	protected void afterUpdate(Role role) {
		super.afterUpdate(role);
		if(role.getRoleUniformResourceLocators().isSynchonizationEnabled()){
			Collection<RoleUniformResourceLocator> database = inject(RoleUniformResourceLocatorDao.class).readByRoles(Arrays.asList(role));	
			delete(RoleUniformResourceLocator.class,database, role.getRoleUniformResourceLocators().getCollection());
			inject(RoleUniformResourceLocatorBusiness.class).save(role.getRoleUniformResourceLocators().getCollection());
		}	
	}
	
	@Override
	protected void beforeDelete(Role role) {
		super.beforeDelete(role);
		inject(RoleUniformResourceLocatorBusiness.class).delete(inject(RoleUniformResourceLocatorDao.class).readByRoles(Arrays.asList(role)));
	}
	
}
