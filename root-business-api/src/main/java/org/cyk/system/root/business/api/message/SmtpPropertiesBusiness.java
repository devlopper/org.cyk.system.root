package org.cyk.system.root.business.api.message;

import java.util.Properties;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.message.SmtpProperties;

public interface SmtpPropertiesBusiness extends TypedBusiness<SmtpProperties> {
	
	Properties convertToProperties(String host,Integer port,String username,String password,Boolean secured);
	Properties convertToProperties(String host,Integer port,String username,String password);
	Properties convertToProperties(SmtpProperties smtpProperties);
	
	Properties findDefaultProperties();
	
	/**/
	
	
}
