package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.impl.message.MailSender;
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
    	inject(MailBusiness.class).pingAll();
    }
}
