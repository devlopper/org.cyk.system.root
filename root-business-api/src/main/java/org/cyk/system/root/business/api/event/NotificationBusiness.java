package org.cyk.system.root.business.api.event;

import java.util.Set;

import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendOptions;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.party.Party;

public interface NotificationBusiness {
	
	void fill(Notification notification,NotificationTemplate template);
	
	void notify(Notification notification,Set<Party> receiver,SendOptions sendOptions);
	
	void notify(Notification notification,String receiverEmail,SendOptions sendOptions);

	void run(Set<RemoteEndPoint> remoteEndPoints);
	
	/**/
	
	/*
	interface UserAccountFinder{
		Collection<UserAccount> find(String[]);
	}*/
}