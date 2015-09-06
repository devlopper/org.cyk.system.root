package org.cyk.system.root.business.api.message;

import java.util.Properties;

import org.cyk.system.root.model.message.SmtpProperties;

public interface MailBusiness extends MessageSendingBusiness {

	Properties convert(SmtpProperties smtpProperties);
	
	SmtpProperties getSmtpProperties();
	
	void setProperties(String localhost,Integer port,String username,String password);
	
}
