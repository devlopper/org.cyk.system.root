package org.cyk.system.root.business.api.message;

import java.util.Properties;


public interface MailBusiness extends MessageSendingBusiness {
	
	Properties getConnectionProperties();
	void setConnectionProperties(Properties properties);
	
	void setConnectionProperties(String host,String from,String username,String password);
	
}
