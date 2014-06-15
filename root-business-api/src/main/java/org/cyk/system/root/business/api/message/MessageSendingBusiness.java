package org.cyk.system.root.business.api.message;

import java.util.Collection;

import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;

public interface MessageSendingBusiness {
	
	void send(Notification notification,String[] theReceiverIds);
	
	void send(Notification notification,String aReceiverId);
	
	void send(Notification notification,Collection<String> theReceiverIds);
	
	void send(Notification notification,Party[] theReceiverIds);
	
	void send(Notification notification,Party aReceiverId);
	
	void sendParty(Notification notification,Collection<Party> theReceiverIds);

}
