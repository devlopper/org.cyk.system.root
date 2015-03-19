package org.cyk.system.root.persistence.api.security;

import java.util.Collection;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.system.root.persistence.api.TypedDao;

public interface UserAccountDao extends TypedDao<UserAccount> {
 
    UserAccount readByCredentials(Credentials credentials);
    
    UserAccount readByUsername(String username);
    
    Collection<UserAccount> readByParties(Collection<Party> parties);
    
    Collection<UserAccount> readByCriteria(UserAccountSearchCriteria searchCriteria);
    
    Long countByCriteria(UserAccountSearchCriteria searchCriteria);
    
}
