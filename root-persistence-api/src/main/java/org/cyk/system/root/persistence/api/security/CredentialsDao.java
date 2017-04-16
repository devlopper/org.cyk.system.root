package org.cyk.system.root.persistence.api.security;


import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.persistence.api.TypedDao;

public interface CredentialsDao extends TypedDao<Credentials> {

	Credentials readBySoftwareByUsername(Software software,String aUsername);
    Credentials readBySoftwareByUsernameByPassword(Software software,String aUsername,String password);
    
}
