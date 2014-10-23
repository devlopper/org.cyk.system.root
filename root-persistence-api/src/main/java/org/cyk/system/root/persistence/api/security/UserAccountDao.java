package org.cyk.system.root.persistence.api.security;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.TypedDao;

public interface UserAccountDao extends TypedDao<UserAccount> {
 
    UserAccount readByCredentials(Credentials credentials);
    
}
