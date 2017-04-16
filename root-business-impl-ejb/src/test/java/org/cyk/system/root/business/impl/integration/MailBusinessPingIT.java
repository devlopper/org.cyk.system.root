package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.impl.message.MailSender;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;
import org.junit.Test;

public class MailBusinessPingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
        
    @Override
    protected void businesses() {}
    
    @Test
    public void pingDefault(){
    	new MailSender().ping();
    }
	
    @Test
    public void pingAll(){
    	for(SmtpProperties smtpProperties : inject(SmtpPropertiesDao.class).readAll()){
    		MailSender sender = new MailSender();
    		sender.setProperties(inject(SmtpPropertiesBusiness.class).convertToProperties(smtpProperties));
    		sender.ping();
    	}
    }
}
