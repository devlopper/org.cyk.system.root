package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;

public class MailBusinessPingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
        
    @Override
    protected void businesses() {
    	for(SmtpProperties smtpProperties : inject(SmtpPropertiesDao.class).readAll())
    		inject(MailBusiness.class).ping(smtpProperties);
    }
	
}
