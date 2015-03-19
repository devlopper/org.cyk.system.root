package org.cyk.system.root.business.api.event;

import java.util.Set;

import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendOptions;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.party.Party;

public interface NotificationBusiness {
	
	public void fill(Notification notification,NotificationTemplate template);
	
	public void notify(Notification notification,Set<Party> receiver,SendOptions sendOptions);
	
	public void notify(Notification notification,String receiverEmail,SendOptions sendOptions);

	/**/
	
	/*
	interface UserAccountFinder{
		Collection<UserAccount> find(String[]);
	}*/
}