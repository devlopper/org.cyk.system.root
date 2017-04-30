package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.UserAccount;

public interface PermissionBusiness extends AbstractEnumerationBusiness<Permission> {
    
	String computeCode(Class<? extends AbstractIdentifiable> aClass,Crud crud); 
	
	Permission find(Class<? extends AbstractIdentifiable> aClass,Crud crud); 
	
	Collection<Permission> findByUsername(String username);
	
	Collection<Permission> findByUserAccount(UserAccount anUserAccount);

}
