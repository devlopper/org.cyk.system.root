package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.TemplateEngineBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendOptions;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.security.UserAccountDao;

public class NotificationBusinessImpl implements NotificationBusiness,Serializable {
    
	private static final long serialVersionUID = -7831396456120834486L;
	@Inject private TemplateEngineBusiness templateEngineBusiness;
    //@Inject 
    private MailBusiness mailBusiness;
    @Inject private Event<Notification> bus;
    @Inject private UserAccountDao userAccountDao;
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
    
}
