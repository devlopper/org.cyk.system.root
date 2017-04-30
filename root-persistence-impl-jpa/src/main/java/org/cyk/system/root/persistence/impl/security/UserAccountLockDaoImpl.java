package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountLock;
import org.cyk.system.root.persistence.api.security.UserAccountLockDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class UserAccountLockDaoImpl extends AbstractTypedDao<UserAccountLock> implements UserAccountLockDao,Serializable {
	
	private static final long serialVersionUID = 4486697640519669942L;
	
	private String readUnlockDateIsNullByUserAccount;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readUnlockDateIsNullByUserAccount, "SELECT userAccountLock FROM UserAccountLock userAccountLock "
        		+ "WHERE userAccountLock.userAccount = :userAccount AND userAccountLock.unlockDate IS NULL");
    }
    
    @Override
    public UserAccountLock readUnlockDateIsNullByUserAccount(UserAccount aUserAccount) {
    	return null;
    }
    
    /*
    @Override
    public UserAccount readByCredentials(Credentials credentials) {
        return namedQuery(readByCredentials).ignoreThrowable(NoResultException.class).parameter("username", credentials.getUsername())
        		.parameter("password", credentials.getPassword())
                .resultOne();
    }

	@SuppressWarnings("unchecked")
	@Override
	public Collection<UserAccount> readByCriteria(UserAccountSearchCriteria searchCriteria) {
		String queryName = null;
		if(searchCriteria.getUsernameSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getUsernameSearchCriteria().getAscendingOrdered())?
					readByCriteriaCreationDateAscendingOrder:readByCriteriaCreationDateDescendingOrder;
		}else
			queryName = readByCriteriaCreationDateAscendingOrder;
		QueryWrapper<?> queryWrapper = namedQuery(queryName);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Collection<UserAccount>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(UserAccountSearchCriteria searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}
	
	*/
}
 