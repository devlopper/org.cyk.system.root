package org.cyk.system.root.business.api.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.internet.InternetAddress;

import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.utility.common.message.Message;

public interface MailBusiness extends MessageSendingBusiness<InternetAddress> {

	void ping(SmtpProperties smtpProperties);
	void pingAll();
	
	void send(Message message,SmtpProperties smtpProperties);
	void send(Message message);
	
	/**/
	
	public static interface Listener extends MessageSendingBusiness.Listener<InternetAddress> {
    	
		Collection<Listener> COLLECTION = new ArrayList<>();
		
    	public static class Adapter extends MessageSendingBusiness.Listener.Adapter<InternetAddress> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
    		
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				
				
			}
    	}
    	
    }
	
	/**/
	
	
	
}
