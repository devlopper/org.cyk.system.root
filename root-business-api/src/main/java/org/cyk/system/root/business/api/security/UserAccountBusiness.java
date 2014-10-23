package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;

public interface UserAccountBusiness extends TypedBusiness<UserAccount> {
    
	UserAccount findByCredentials(Credentials credentials);
    
	UserAccount connect(Credentials credentials);
	
	void disconnect(UserAccount userAccount);
	
}
