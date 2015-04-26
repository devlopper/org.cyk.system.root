package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import lombok.extern.java.Log;

import org.cyk.system.root.business.api.event.EventReminderBusiness;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.TemplateEngineBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendOptions;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.event.EventParticipationDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.security.UserAccountDao;

@Log
public class NotificationBusinessImpl implements NotificationBusiness,Serializable {
    
	private static final long serialVersionUID = -7831396456120834486L;
	@Inject private TemplateEngineBusiness templateEngineBusiness;
    @Inject private MailBusiness mailBusiness;
    @Inject private Event<Notification> bus;
    @Inject private EventReminderBusiness eventReminderBusiness;
    @Inject private UserAccountDao userAccountDao;
    @Inject private EventParticipationDao eventParticipationDao;
    @Inject private ContactDao contactDao;
    @Inject private TimeBusiness timeBusiness;

    @Override
    public void fill(Notification notification,NotificationTemplate aNotificationTemplate) {
        notification.setTitle(templateEngineBusiness.process(aNotificationTemplate.getTitle(), aNotificationTemplate.getTitleParametersMap()));
        notification.setMessage(templateEngineBusiness.process(aNotificationTemplate.getMessage(), aNotificationTemplate.getMessageParametersMap()));
    }
    
    private void notify(Notification notification, String[] theReceiverIds,SendOptions sendOptions) {
    	notification.setDate(timeBusiness.findUniversalTimeCoordinated());
        if(RemoteEndPoint.MAIL_SERVER.equals(notification.getRemoteEndPoint())){
            mailBusiness.send(notification, theReceiverIds,sendOptions);
        }
        if(RemoteEndPoint.PHONE.equals(notification.getRemoteEndPoint())){
            
        }
        if(RemoteEndPoint.USER_INTERFACE.equals(notification.getRemoteEndPoint())){
            
        }
        //Let us notify the observer to that notification
        bus.fire(notification);
    }
    
    @Override
    public void notify(Notification notification, Set<Party> theReceivers,SendOptions sendOptions) {
    	notification.getUserAccounts().clear();
    	for(UserAccount userAccount : userAccountDao.readByParties(theReceivers)){
    		notification.getUserAccounts().add(userAccount);
    	}
    	
    	Collection<ContactCollection> contactCollections = new ArrayList<>();
    	for(Party party : theReceivers)
    		contactCollections.add(party.getContactCollection());
    	
    	Set<String> theReceiverIds = new HashSet<>();
    	
    	if(RemoteEndPoint.MAIL_SERVER.equals(notification.getRemoteEndPoint())){
    		for(ElectronicMail electronicMail : contactDao.readAllByCollections(ElectronicMail.class, contactCollections))
    			theReceiverIds.add(electronicMail.getAddress());
    	}
    	
    	notify(notification, theReceiverIds.toArray(new String[]{}), sendOptions);
    }

    @Override
    public void notify(Notification notification, String receiverEmail,SendOptions sendOptions) {
        
    	notify(notification, new String[]{receiverEmail}, sendOptions);
    }
    
    @Override
    public void run(Set<RemoteEndPoint> remoteEndPoints) {
    	Collection<EventReminder> eventReminders = eventReminderBusiness.findCurrents();
    	Collection<org.cyk.system.root.model.event.Event> events = new ArrayList<org.cyk.system.root.model.event.Event>();
    	for(EventReminder eventReminder : eventReminders)
    		events.add(eventReminder.getEvent());
		//System.out.println("Alarms : "+events.size());
		Collection<EventParticipation> eventParticipations = eventParticipationDao.readByEvents(events);
		for(org.cyk.system.root.model.event.Event event : events){
			for(RemoteEndPoint remoteEndPoint : remoteEndPoints){
				if(remoteEndPoint.alarmTemplate==null){
					log.severe("No alarm template found");
				}else{
					Set<Party> parties = new HashSet<>();
					for(EventParticipation eventParticipation : eventParticipations)
						if(eventParticipation.getEvent().equals(event))
							if(Boolean.TRUE.equals(eventParticipation.getAlertParty()))
								parties.add(eventParticipation.getParty());
					
					Notification notification = new Notification();
					notification.setRemoteEndPoint(remoteEndPoint);
					NotificationTemplate template = remoteEndPoint.alarmTemplate;
					template.getTitleParametersMap().put("title", event.getType().getName());
					template.getMessageParametersMap().put("body", event.getObject());
					fill(notification, template);
					SendOptions sendOptions = new SendOptions();
					
					notify(notification, parties, sendOptions);
				}
			}
		}
    }
    
}
