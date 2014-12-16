package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.persistence.api.security.RoleDao;

public class RoleBusinessImpl extends AbstractEnumerationBusinessImpl<Role, RoleDao> implements RoleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public RoleBusinessImpl(RoleDao dao) {
		super(dao); 
	}  

}
