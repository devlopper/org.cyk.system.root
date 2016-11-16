package org.cyk.system.root.business.api.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendOptions;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface NotificationBusiness extends BusinessService {
	
	void fill(Notification notification,NotificationTemplate template);
	
	void notify(Notification notification,Set<Party> receiver,SendOptions sendOptions);
	
	void notify(Notification notification,String receiverEmail,SendOptions sendOptions);

	void notify(Collection<Notification> notifications,SendOptions sendOptions);
	void notify(Collection<Notification> notifications);
	
	void run(Set<RemoteEndPoint> remoteEndPoints);
	
	void notify(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,NotifyListener listener);
	
	public static interface NotifyListener {
		Collection<Notification> getNotifications(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
		Notification getNotification(AbstractIdentifiable identifiable,RemoteEndPoint remoteEndPoint);
		SendOptions getSendOptions();
		
		public static class Adapter extends BeanAdapter implements NotifyListener,Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public Collection<Notification> getNotifications(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
				return null;
			}

			@Override
			public Notification getNotification(AbstractIdentifiable identifiable, RemoteEndPoint remoteEndPoint) {
				return null;
			}
			
			@Override
			public SendOptions getSendOptions() {
				return null;
			}
			
			/**/
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Collection<Notification> getNotifications(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
					Collection<Notification> notifications = new ArrayList<>();
					for(AbstractIdentifiable identifiable : identifiables)
						notifications.add(getNotification(identifiable, remoteEndPoint));
					return notifications;
				}

				@Override
				public Notification getNotification(AbstractIdentifiable identifiable, RemoteEndPoint remoteEndPoint) {
					return null;
				}
				
				@Override
				public SendOptions getSendOptions() {
					return new SendOptions();
				}
				
			}
		}
	}
	
}