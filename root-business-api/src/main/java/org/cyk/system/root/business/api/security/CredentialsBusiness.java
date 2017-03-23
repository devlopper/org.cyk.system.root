package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.Credentials;

public interface CredentialsBusiness extends TypedBusiness<Credentials> {
    
	Credentials findByUsername(String aUsername);    
    Credentials findByUsername(String aUsername,String password);
    Credentials instanciateOne(String username,String password);
}
