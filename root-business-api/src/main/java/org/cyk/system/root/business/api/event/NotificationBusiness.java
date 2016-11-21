package org.cyk.system.root.business.api.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendArguments;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface NotificationBusiness extends BusinessService {
	
	void fill(Notification notification,NotificationTemplate template);
	
	void notify(Notification notification,Set<Party> receiver,SendArguments sendOptions);
	
	void notify(Notification notification,String receiverEmail,SendArguments sendOptions);

	void notify(Collection<Notification> notifications,SendArguments sendOptions);
	void notify(Collection<Notification> notifications);
	
	void run(Set<RemoteEndPoint> remoteEndPoints);
	
	void notify(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
	
	public static interface Listener {
		
		public Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;

			
			/**/
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				
				
			}
		}
	}
	
}