package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;

public interface UserAccountBusiness extends TypedBusiness<UserAccount> {
    
	UserAccount findByCredentials(Credentials credentials);
    
	Collection<UserAccount> findByCriteria(UserAccountSearchCriteria criteria);
	
	Long countByCriteria(UserAccountSearchCriteria criteria);
	
	UserAccount connect(Credentials credentials);
	
	void disconnect(UserAccount userAccount);
	
	String findStatus(UserAccount userAccount);
	
}
