package org.cyk.system.root.persistence.impl.security;

import static org.cyk.utility.common.computation.ArithmeticOperator.EQ;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.UserAccountDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.utility.common.computation.LogicalOperator;

public class UserAccountDaoImpl extends AbstractTypedDao<UserAccount> implements UserAccountDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByCredentials;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByCredentials, _select().where("credentials.username","username",EQ).where(LogicalOperator.AND,"credentials.password","password",EQ));
    }
    
    @Override
    public UserAccount readByCredentials(Credentials credentials) {
        return namedQuery(readByCredentials).ignoreThrowable(NoResultException.class).parameter("username", credentials.getUsername())
        		.parameter("password", credentials.getPassword())
                .resultOne();
    }

}
 