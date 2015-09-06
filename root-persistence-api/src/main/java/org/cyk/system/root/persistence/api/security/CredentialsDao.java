package org.cyk.system.root.persistence.api.security;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.persistence.api.TypedDao;

public interface CredentialsDao extends TypedDao<Credentials> {

    Credentials readByUsername(String aUsername);
    
    Credentials readByUsernameByPassword(String aUsername,String password);
    
}
