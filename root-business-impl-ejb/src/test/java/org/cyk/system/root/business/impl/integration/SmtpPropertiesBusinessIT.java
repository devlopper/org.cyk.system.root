package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.event.Notification;
import org.junit.Test;

public class SmtpPropertiesBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
	@Override
	protected void businesses() {
		
	}
	
	@Test
    public void pingDefault(){
		Notification notification = new Notification();
		notification.setTitle("Ping!");
		notification.setMessage("This is a ping.");
		notification.addReceiverIdentifiers("kycdev@gmail.com");
		inject(MailBusiness.class).send(notification);
    }
	
	

}
