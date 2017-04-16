package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.persistence.api.security.CredentialsDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class CredentialsDaoImpl extends AbstractTypedDao<Credentials> implements CredentialsDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
   private String readBySoftwareByUsername,readBySoftwareByUsernameByPassword;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readBySoftwareByUsername, _select().where(Credentials.FIELD_SOFTWARE).and(Credentials.FIELD_USERNAME));
        registerNamedQuery(readBySoftwareByUsernameByPassword, _select().where(Credentials.FIELD_SOFTWARE).and(Credentials.FIELD_USERNAME).and(Credentials.FIELD_PASSWORD));
    }
    
    @Override
	public Credentials readBySoftwareByUsername(Software software, String aUsername) {
    	return namedQuery(readBySoftwareByUsername).ignoreThrowable(NoResultException.class).parameter(Credentials.FIELD_SOFTWARE, software)
    			.parameter(Credentials.FIELD_USERNAME, aUsername)
                .resultOne();
	}
    
	@Override
	public Credentials readBySoftwareByUsernameByPassword(Software software, String aUsername,String password) {
		return namedQuery(readBySoftwareByUsernameByPassword).ignoreThrowable(NoResultException.class).parameter(Credentials.FIELD_SOFTWARE, software)
    			.parameter(Credentials.FIELD_USERNAME, aUsername).parameter(Credentials.FIELD_PASSWORD, password)
                .resultOne();
	}
    
   

}
 