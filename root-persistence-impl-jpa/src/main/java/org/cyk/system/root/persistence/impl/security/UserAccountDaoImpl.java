package org.cyk.system.root.persistence.impl.security;

import static org.cyk.utility.common.computation.ArithmeticOperator.EQ;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.system.root.persistence.api.security.UserAccountDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.Utils;

public class UserAccountDaoImpl extends AbstractTypedDao<UserAccount> implements UserAccountDao,Serializable {

	private static final String READ_BY_CRITERIA_SELECT_FORMAT = "SELECT userAccount FROM UserAccount userAccount ";
	private static final String READ_BY_CRITERIA_WHERE_FORMAT = "WHERE userAccount.credentials.username = :username ";
	private static final String WHERE_EXCLUDE_ROLE = "NOT EXISTS (SELECT role FROM Role role WHERE role MEMBER OF userAccount.roles AND role.identifier IN :identifiers) ";
	
	private static final String READ_BY_CRITERIA_NOTORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT+" AND "+WHERE_EXCLUDE_ROLE;
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_NOTORDERED_FORMAT+ORDER_BY_FORMAT;
	
	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByCredentials,readAllSortedByDate,readByCriteriaCreationDateAscendingOrder,readByCriteriaCreationDateDescendingOrder
		,readByParties,readByUsername,readAllExcludeRoles,countAllExcludeRoles;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByCredentials, _select().where(commonUtils.attributePath(UserAccount.FIELD_CREDENTIALS, Credentials.FIELD_SOFTWARE),Credentials.FIELD_SOFTWARE)
        		.and(commonUtils.attributePath(UserAccount.FIELD_CREDENTIALS, Credentials.FIELD_USERNAME),Credentials.FIELD_USERNAME,EQ)
        		.and(commonUtils.attributePath(UserAccount.FIELD_CREDENTIALS, Credentials.FIELD_PASSWORD),Credentials.FIELD_PASSWORD,EQ));
        registerNamedQuery(readAllSortedByDate,READ_BY_CRITERIA_SELECT_FORMAT+" ORDER BY userAccount.globalIdentifier.creationDate DESC");
        registerNamedQuery(readAllExcludeRoles,READ_BY_CRITERIA_SELECT_FORMAT+" WHERE "+WHERE_EXCLUDE_ROLE);
        registerNamedQuery(readByCriteria,READ_BY_CRITERIA_NOTORDERED_FORMAT);
        registerNamedQuery(readByCriteriaCreationDateAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "userAccount.globalIdentifier.creationDate ASC") );
        registerNamedQuery(readByCriteriaCreationDateDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "userAccount.globalIdentifier.creationDate DESC") );
        registerNamedQuery(readByParties,"SELECT userAccount FROM UserAccount userAccount WHERE userAccount.user.identifier IN :identifiers" );
        registerNamedQuery(readByUsername,"SELECT userAccount FROM UserAccount userAccount WHERE LOWER(userAccount.credentials.username) = LOWER(:username)");
    }
    
    @Override
    public UserAccount readByCredentials(Credentials credentials) {
        return namedQuery(readByCredentials).ignoreThrowable(NoResultException.class).parameter(Credentials.FIELD_SOFTWARE, credentials.getSoftware())
        		.parameter(Credentials.FIELD_USERNAME, credentials.getUsername()).parameter(Credentials.FIELD_PASSWORD, credentials.getPassword())
            .resultOne();
    }
    
    @Override
    public UserAccount readByUsername(String username) {
    	return namedQuery(readByUsername).ignoreThrowable(NoResultException.class).parameter("username", username)
            .resultOne();
    }
    
    @Override
    public Collection<UserAccount> readByParties(Collection<Party> parties) {
    	return namedQuery(readByParties).parameterIdentifiers(parties).resultMany();
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
	
	/**/
	
	protected void applyCriteriaParameters(QueryWrapper<?> queryWrapper,UserAccountSearchCriteria searchCriteria){
		queryWrapper.parameter("username",searchCriteria.getUsernameSearchCriteria().getPreparedValue());
		queryWrapper.parameter("identifiers", Utils.ids(searchCriteria.getRoleExcluded()));
	}

	@Override
	public Collection<UserAccount> readAllExcludeRoles(Collection<Role> roles) {
		return namedQuery(readAllExcludeRoles).parameterIdentifiers(roles).resultMany();
	}

	@Override
	public Long countAllExcludeRoles(Collection<Role> roles) {
		return countNamedQuery(countAllExcludeRoles).parameterIdentifiers(roles).resultOne();
	}

}
 