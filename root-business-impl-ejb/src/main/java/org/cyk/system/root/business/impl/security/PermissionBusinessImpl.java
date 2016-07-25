package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.PermissionDao;

public class PermissionBusinessImpl extends AbstractEnumerationBusinessImpl<Permission, PermissionDao> implements PermissionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PermissionBusinessImpl(PermissionDao dao) {
		super(dao); 
	}

	@Override
	public String computeCode(Class<? extends AbstractIdentifiable> aClass,Crud crud) {
		return aClass.getSimpleName()+":"+crud.name();
	}

	@Override
	public Permission find(Class<? extends AbstractIdentifiable> aClass,Crud crud) {
		return findByGlobalIdentifierCode(computeCode(aClass, crud));
	}  

	@Override
	public Collection<Permission> findByUserAccount(UserAccount anUserAccount) {
		return dao.readByUserAccount(anUserAccount);
	}
	
	@Override
	public Collection<Permission> findByUsername(String username) {
		return dao.readByUsername(username);
	}
}
