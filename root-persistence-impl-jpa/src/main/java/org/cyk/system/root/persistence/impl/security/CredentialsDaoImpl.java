package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.persistence.api.security.CredentialsDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;

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
    
	@Override
	protected String getReadByCriteriaQuery(String query) {
		return super.getReadByCriteriaQuery(query)+" OR "+QueryStringBuilder.getLikeString("record.username", ":username");
	}
	
	@Override
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where) {
		return super.getReadByCriteriaQueryCodeExcludedWherePart(where)+" OR "+QueryStringBuilder.getLikeString("record.username", ":username");
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameterLike(Credentials.FIELD_USERNAME, ((Credentials.SearchCriteria)searchCriteria).getUsername());
	}

}
 