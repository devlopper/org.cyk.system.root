package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.RoleUniformResourceLocatorDao;

public class RoleUniformResourceLocatorBusinessImpl extends AbstractTypedBusinessService<RoleUniformResourceLocator, RoleUniformResourceLocatorDao> implements RoleUniformResourceLocatorBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private UniformResourceLocatorBusiness uniformResourceLocatorBusiness;
	
	@Inject
	public RoleUniformResourceLocatorBusinessImpl(RoleUniformResourceLocatorDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<RoleUniformResourceLocator> findByRoles(Collection<Role> roles) {
		return dao.readByRoles(roles);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public RoleUniformResourceLocator findByRoles(URL url,Collection<Role> roles) {
		ArrayList<RoleUniformResourceLocator> roleUniformResourceLocators = new ArrayList<>(dao.readByRoles(roles));
		Collection<UniformResourceLocator> uniformResourceLocators = new ArrayList<>();
		for(RoleUniformResourceLocator roleUniformResourceLocator : roleUniformResourceLocators)
			uniformResourceLocators.add(roleUniformResourceLocator.getUniformResourceLocator());
		logTrace("URL of {} : {}",roles,uniformResourceLocators);
		UniformResourceLocator uniformResourceLocator = uniformResourceLocatorBusiness.find(url,uniformResourceLocators);
		if(uniformResourceLocator!=null)
			for(RoleUniformResourceLocator roleUniformResourceLocator : roleUniformResourceLocators)
				if(roleUniformResourceLocator.getUniformResourceLocator().getIdentifier().equals(uniformResourceLocator.getIdentifier()))
					return roleUniformResourceLocator;
		return null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public RoleUniformResourceLocator findByUserAccount(URL url,UserAccount userAccount) {
		return findByRoles(url,userAccount.getRoles());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAccessible(URL url, Collection<Role> roles) {
		return findByRoles(url, roles)!=null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAccessibleByUserAccount(URL url, UserAccount userAccount) {
		return findByUserAccount(url, userAccount)!=null;
	}
	
	
}
