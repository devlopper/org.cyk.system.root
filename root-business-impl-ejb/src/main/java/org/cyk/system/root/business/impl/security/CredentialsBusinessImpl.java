package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.CredentialsBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.persistence.api.security.CredentialsDao;

public class CredentialsBusinessImpl extends AbstractTypedBusinessService<Credentials, CredentialsDao> implements CredentialsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public CredentialsBusinessImpl(CredentialsDao dao) {
		super(dao); 
	}  

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Credentials findByUsername(String aUsername) {
		return dao.readByUsername(aUsername);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Credentials findByUsername(String aUsername, String password) {
		return dao.readByUsernameByPassword(aUsername, password);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Credentials instanciateOne(String username, String password) {
		Credentials credentials = instanciateOne();
		credentials.setUsername(username);
		credentials.setPassword(password);
		return credentials;
	}
	
	@Override
	protected Credentials __instanciateOne__(String[] values,InstanciateOneListener<Credentials> listener) {
		set(listener.getSetListener(), Credentials.FIELD_USERNAME);
    	set(listener.getSetListener(), Credentials.FIELD_PASSWORD);
    	return listener.getInstance();
	}
	
}
