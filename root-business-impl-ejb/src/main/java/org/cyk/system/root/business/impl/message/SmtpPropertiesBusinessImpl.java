package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.api.security.CredentialsBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.message.SmtpSocketFactory;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;

public class SmtpPropertiesBusinessImpl extends AbstractTypedBusinessService<SmtpProperties, SmtpPropertiesDao> implements SmtpPropertiesBusiness,Serializable {

	private static final long serialVersionUID = 8464902693612253709L;

	@Inject
	public SmtpPropertiesBusinessImpl(SmtpPropertiesDao dao) {
		super(dao);
	}
	
	@Override
	protected SmtpProperties __instanciateOne__(String[] values,org.cyk.system.root.business.api.TypedBusiness.InstanciateOneListener<SmtpProperties> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		listener.getInstance().setCredentials(new Credentials());
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
    	set(listener.getSetListener(), SmtpProperties.FIELD_HOST);
    	set(listener.getSetListener(), SmtpProperties.FIELD_PORT);
    	set(listener.getSetListener(), SmtpProperties.FIELD_FROM);
    	set(listener.getSetListener(), SmtpProperties.FIELD_CREDENTIALS,Credentials.FIELD_USERNAME);
    	set(listener.getSetListener(), SmtpProperties.FIELD_CREDENTIALS,Credentials.FIELD_PASSWORD);
    	set(listener.getSetListener(), SmtpProperties.FIELD_SOCKET_FACTORY,SmtpSocketFactory.FIELD_CLAZZ);
    	set(listener.getSetListener(), SmtpProperties.FIELD_SOCKET_FACTORY,SmtpSocketFactory.FIELD_FALLBACK);
    	set(listener.getSetListener(), SmtpProperties.FIELD_SOCKET_FACTORY,SmtpSocketFactory.FIELD_PORT);
    	listener.getInstance().setAuthenticated(StringUtils.isNotBlank(listener.getInstance().getCredentials().getPassword()));
		return listener.getInstance();
	}
	
	@Override
	protected void beforeCreate(SmtpProperties smtpProperties) {
		super.beforeCreate(smtpProperties);
		createIfNotIdentified(smtpProperties.getCredentials());
	}
	
	@Override
	protected void afterUpdate(SmtpProperties smtpProperties) {
		super.afterUpdate(smtpProperties);
		inject(CredentialsBusiness.class).update(smtpProperties.getCredentials());
	}
	
}
