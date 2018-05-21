package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.api.security.CredentialsBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;
import org.cyk.utility.common.helper.FieldHelper;

public class SmtpPropertiesBusinessImpl extends AbstractTypedBusinessService<SmtpProperties, SmtpPropertiesDao> implements SmtpPropertiesBusiness,Serializable {

	private static final long serialVersionUID = 8464902693612253709L;

	@Inject
	public SmtpPropertiesBusinessImpl(SmtpPropertiesDao dao) {
		super(dao);
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
	
	@Override
	public Properties convertToProperties(String host, Integer port, String username, String password,Boolean secured) {
		Properties properties = new Properties();
		addProperty(properties, RootConstant.Configuration.SmtpProperties.HOST, host);
		addProperty(properties, RootConstant.Configuration.SmtpProperties.FROM, username);
		addProperty(properties, RootConstant.Configuration.SmtpProperties.USER, username);
		addProperty(properties, RootConstant.Configuration.SmtpProperties.PASSWORD, password);
		
		//addProperty(properties, "socketFactory.port", smtpProperties.getSocketFactory().getPort());
		addProperty(properties, RootConstant.Configuration.SmtpProperties.PORT, port);
		//addProperty(properties, "socketFactory.fallback", smtpProperties.getSocketFactory().getFallback());
		addProperty(properties, RootConstant.Configuration.SmtpProperties.AUTH, StringUtils.isNotBlank(password));
		//addProperty(properties, "socketFactory.class", smtpProperties.getSocketFactory().getClazz());
		
		if(Boolean.TRUE.equals(secured)){
			addProperty(properties, RootConstant.Configuration.SmtpProperties.STARTTLS_ENABLE, secured);
			addProperty(properties, RootConstant.Configuration.SmtpProperties.SSL_ENABLE, secured);
		}
		return properties;
	}
	
	@Override
	public Properties convertToProperties(String host, Integer port, String username, String password) {
		return convertToProperties(host, port, username, password,Boolean.TRUE);
	}
	
	@Override
	public Properties convertToProperties(SmtpProperties smtpProperties) {
		return convertToProperties(smtpProperties.getService().getComputer().getIpAddressName(), smtpProperties.getService().getPort(), smtpProperties.getCredentials().getUsername()
				, smtpProperties.getCredentials().getPassword(), smtpProperties.getService().getSecured());
	}
	
	private void addProperty(Properties properties,String name,Object value){
    	properties.put(RootConstant.Configuration.SmtpProperties.getProperty(name,Boolean.FALSE), value);
    	properties.put(RootConstant.Configuration.SmtpProperties.getProperty(name,Boolean.TRUE), value);
    }
	
	@Override
	public Properties findDefaultProperties() {
		return convertToProperties(findDefaulted());
	}
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<SmtpProperties> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(SmtpProperties.class);
			addFieldCode().addParameterArrayElementString(SmtpProperties.FIELD_SERVICE,SmtpProperties.FIELD_CREDENTIALS,SmtpProperties.FIELD_FROM,
	    			FieldHelper.getInstance().buildPath(SmtpProperties.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_DEFAULTED));
		}
		
	}
	
}
