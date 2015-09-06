package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;

public interface UserAccountBusiness extends TypedBusiness<UserAccount> {
    
	UserAccount findByCredentials(Credentials credentials);
	UserAccount findByUsername(String username);
    
	Collection<UserAccount> findByCriteria(UserAccountSearchCriteria criteria);
	Long countByCriteria(UserAccountSearchCriteria criteria);
	
	Collection<UserAccount> findAllExcludeRoles(Collection<Role> roles);
    Long countAllExcludeRoles(Collection<Role> roles);
	
	UserAccount connect(Credentials credentials);
	
	void disconnect(UserAccount userAccount);
	
	String findStatus(UserAccount userAccount);
	
	Boolean createSessionNotification(UserAccount userAccount,Notification notification);
	void deleteSessionNotification(UserAccount userAccount,Notification notification);
	
	//void updatePassword(UserAccount userAccount,String oldPassword,String newPassword);
	
	/**/
	
	String EXCEPTION_ALREADY_CONNECTED = "ALREADY_CONNECTED";
}
