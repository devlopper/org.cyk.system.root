package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Software;

public interface CredentialsBusiness extends TypedBusiness<Credentials> {
    
	Credentials findBySoftwareByUsername(Software software,String aUsername);
	Credentials findByUsername(String aUsername); 
	
	Credentials findBySoftwareByUsernameByPassword(Software software,String aUsername,String password);
	   
    Credentials instanciateOne(String username,String password);
    
}
