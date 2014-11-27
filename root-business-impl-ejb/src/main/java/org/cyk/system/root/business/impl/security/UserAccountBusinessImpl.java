package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.UserAccountDao;

@Stateless
public class UserAccountBusinessImpl extends AbstractTypedBusinessService<UserAccount, UserAccountDao> implements UserAccountBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserAccountBusinessImpl(UserAccountDao dao) {
		super(dao); 
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
}
