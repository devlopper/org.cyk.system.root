package org.cyk.system.root.business.api.security;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
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
	
	Boolean hasAtLeastOneRole(UserAccount userAccount,Collection<Role> roles);
	Boolean hasRole(UserAccount userAccount,Role role);
	
	//void updatePassword(UserAccount userAccount,String oldPassword,String newPassword);
	
	UserAccount instanciateOne(Party party,Role...roles);
	Collection<UserAccount> instanciateManyFromParties(Collection<Party> parties,Role...roles);
	Collection<UserAccount> instanciateManyFromActors(Collection<? extends AbstractActor> actors,Role...roles);
	
	/**/
	
	String EXCEPTION_ALREADY_CONNECTED = "ALREADY_CONNECTED";
}
