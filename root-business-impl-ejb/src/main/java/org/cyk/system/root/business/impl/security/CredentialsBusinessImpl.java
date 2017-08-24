package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
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
	protected Object[] getPropertyValueTokens(Credentials credentials, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{credentials.getSoftware(),credentials.getUsername()};
		return super.getPropertyValueTokens(credentials, name);
	}
	
	@Override
	protected void beforeCreate(Credentials credentials) {
		if(credentials.getSoftware()==null)
			credentials.setSoftware(inject(SoftwareBusiness.class).findDefaulted());
		super.beforeCreate(credentials);
		
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Credentials findByUsername(String aUsername) {
		return dao.readBySoftwareByUsername(inject(SoftwareBusiness.class).findDefaulted(),aUsername);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Credentials findBySoftwareByUsername(Software software, String aUsername) {
		return dao.readBySoftwareByUsername(software, aUsername);
	}
	
	@Override
	public Credentials instanciateOne() {
		Credentials credentials = super.instanciateOne();
		credentials.setSoftware(inject(SoftwareBusiness.class).findDefaulted());
		return credentials;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Credentials instanciateOne(String username, String password) {
		Software software = inject(SoftwareBusiness.class).findDefaulted();
		return instanciateOne(new String[]{null,software == null ? null : software.getCode(),username,password});
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
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<Credentials> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Credentials.class);
			addFieldCode().addParameterArrayElementString(Credentials.FIELD_SOFTWARE,Credentials.FIELD_USERNAME,Credentials.FIELD_PASSWORD);
		}
		
	}
			
}
