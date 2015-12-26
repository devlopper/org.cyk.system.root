package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.system.root.persistence.api.security.RoleUniformResourceLocatorDao;

@Stateless
public class RoleBusinessImpl extends AbstractEnumerationBusinessImpl<Role, RoleDao> implements RoleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private RoleUniformResourceLocatorDao roleUniformResourceLocatorDao;
	
	@Inject
	public RoleBusinessImpl(RoleDao dao) {
		super(dao); 
	}  

	@Override
	public Role create(Role role) {
		role = super.create(role);
		save(role);
		return role;
	}
	
	private void save(Role role){
		for(RoleUniformResourceLocator roleUniformResourceLocator : role.getRoleUniformResourceLocators()){
			roleUniformResourceLocator.setRole(role);
			if(roleUniformResourceLocator.getIdentifier()==null)
				roleUniformResourceLocatorDao.create(roleUniformResourceLocator);
			else
				roleUniformResourceLocatorDao.update(roleUniformResourceLocator);		
		}
	}
	
	@Override
	public Role save(Role role,Collection<RoleUniformResourceLocator> roleUniformResourceLocators) {
		role = dao.update(role);
		role.setRoleUniformResourceLocators(new HashSet<>(roleUniformResourceLocators));
		
		Collection<RoleUniformResourceLocator> database = roleUniformResourceLocatorDao.readByRoles(Arrays.asList(role));
		
		delete(RoleUniformResourceLocator.class,roleUniformResourceLocatorDao,database, role.getRoleUniformResourceLocators());
		
		save(role);
		return role;
	}
	
}
