package org.cyk.system.root.business.impl.integration;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.junit.Test;

public class MailBusinessDatabaseConfigIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    
    @Override
    protected void populate() {
    	super.populate();
    	//inject(MailBusiness.class).setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
    }
    
    @Override
    protected void businesses() {
    	
    }
    
	@Test
    public void send(){
		Collection<Notification> notifications = new ArrayList<>();
		notifications.add(new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER).setTitle("T1").setMessage("M1")
				.addReceiverIdentifiers("kycdev@gmail.com").build());
		
    	inject(MailBusiness.class).send(notifications);
    }
	
}
