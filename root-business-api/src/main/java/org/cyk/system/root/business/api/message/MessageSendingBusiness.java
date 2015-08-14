package org.cyk.system.root.business.api.message;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;

public interface MessageSendingBusiness extends BusinessService {
	
	void send(Notification notification,String[] theReceiverIds,SendOptions options);
	void send(Notification notification,String[] theReceiverIds);
	
	void send(Notification notification,String aReceiverId,SendOptions options);
	void send(Notification notification,String aReceiverId);
	
	void send(Notification notification,Collection<String> theReceiverIds,SendOptions options);
	void send(Notification notification,Collection<String> theReceiverIds);
	
	void send(Notification notification,Party[] theReceiverIds,SendOptions options);
	void send(Notification notification,Party[] theReceiverIds);
	
	void send(Notification notification,Party aReceiverId,SendOptions options);
	void send(Notification notification,Party aReceiverId);
	
	void sendParty(Notification notification,Collection<Party> theReceiverIds,SendOptions options);
	void sendParty(Notification notification,Collection<Party> theReceiverIds);

	/**/
	
	@Getter @Setter
	public static class SendOptions{
		
		public static Boolean BLOCKING = Boolean.FALSE;
		
		private Boolean blocking=BLOCKING;
	}
	
}
