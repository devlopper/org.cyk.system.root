package org.cyk.system.root.persistence.impl.security;

import static org.cyk.utility.common.computation.ArithmeticOperator.EQ;
import static org.cyk.utility.common.computation.LogicalOperator.AND;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.persistence.api.security.CredentialsDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class CredentialsDaoImpl extends AbstractTypedDao<Credentials> implements CredentialsDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
   private String readByUsername,readByUsernameByPassword;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByUsername, _select().where("username",EQ));
        registerNamedQuery(readByUsernameByPassword, _select().where("username",EQ).where(AND,"password", "password",EQ));
    }
    
    @Override
    public Credentials readByUsername(String username) {
        return namedQuery(readByUsername).ignoreThrowable(NoResultException.class).parameter("username", username)
                .resultOne();
    }
    
    @Override
    public Credentials readByUsernameByPassword(String username,String password) {
        return namedQuery(readByUsernameByPassword).ignoreThrowable(NoResultException.class).parameter("username", username).parameter("password", password)
                .resultOne();
    }
    
   

}
 