package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.system.root.persistence.api.security.UserAccountDao;

@Stateless
public class UserAccountBusinessImpl extends AbstractTypedBusinessService<UserAccount, UserAccountDao> implements UserAccountBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private LanguageBusiness languageBusiness;
	
	@Inject
	public UserAccountBusinessImpl(UserAccountDao dao) {
		super(dao); 
	}  

	@Override
	public UserAccount create(UserAccount userAccount) {
		userAccount.setCreationDate(universalTimeCoordinated());
		return super.create(userAccount); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public UserAccount findByCredentials(Credentials credentials) {
		return dao.readByCredentials(credentials);
	}

	@Override
	public UserAccount connect(Credentials credentials) {
		UserAccount account = findByCredentials(credentials);
		exceptionUtils().exception(account==null, "exception.useraccount.unknown");
		return account;
	}

	@Override
	public void disconnect(UserAccount userAccount) {
		
	}
	
	@Override
	public String findStatus(UserAccount userAccount) {
		if(userAccount.getCurrentLock()==null)
			return languageBusiness.findText("enabled");
		return languageBusiness.findText("locked");
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<UserAccount> findByCriteria(UserAccountSearchCriteria criteria) {
		Collection<UserAccount> userAccounts;
		if(StringUtils.isBlank(criteria.getUsernameSearchCriteria().getPreparedValue()))
			userAccounts = dao.readAll();
		else
			userAccounts = dao.readByCriteria(criteria);
		for(UserAccount userAccount : userAccounts)
			userAccount.setStatus(findStatus(userAccount));
		return userAccounts;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(UserAccountSearchCriteria criteria) {
		if(StringUtils.isBlank(criteria.getUsernameSearchCriteria().getPreparedValue()))
			return dao.countAll();
		return dao.countByCriteria(criteria);
	}
}
