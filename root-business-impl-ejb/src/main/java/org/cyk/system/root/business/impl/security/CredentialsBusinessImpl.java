package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.CredentialsBusiness;
import org.cyk.system.root.business.api.security.SoftwareBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.persistence.api.security.CredentialsDao;

public class CredentialsBusinessImpl extends AbstractTypedBusinessService<Credentials, CredentialsDao> implements CredentialsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public CredentialsBusinessImpl(CredentialsDao dao) {
		super(dao); 
	}  
	
	@Override
	protected void beforeCreate(Credentials credentials) {
		super.beforeCreate(credentials);
		if(credentials.getSoftware()==null)
			credentials.setSoftware(inject(SoftwareBusiness.class).findDefault());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Credentials findByUsername(String aUsername) {
		return dao.readBySoftwareByUsername(inject(SoftwareBusiness.class).findDefault(),aUsername);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Credentials findBySoftwareByUsername(Software software, String aUsername) {
		return dao.readBySoftwareByUsername(software, aUsername);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Credentials instanciateOne(String username, String password) {
		return instanciateOne(new String[]{null,inject(SoftwareBusiness.class).findDefaultCode(),username,password});
	}
	
	@Override
	protected Credentials __instanciateOne__(String[] values,InstanciateOneListener<Credentials> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), Credentials.FIELD_SOFTWARE);
		set(listener.getSetListener(), Credentials.FIELD_USERNAME);
    	set(listener.getSetListener(), Credentials.FIELD_PASSWORD);
    	return listener.getInstance();
	}

	@Override
	public Credentials findBySoftwareByUsernameByPassword(Software software, String aUsername, String password) {
		return dao.readBySoftwareByUsernameByPassword(software, aUsername, password);
	}
	
}
