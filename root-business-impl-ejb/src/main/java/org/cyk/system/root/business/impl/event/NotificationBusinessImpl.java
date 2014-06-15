package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.TemplateEngineBusiness;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.party.Party;

public class NotificationBusinessImpl implements NotificationBusiness,Serializable {
    
    @Inject private TemplateEngineBusiness templateEngineBusiness;
    @Inject private MailBusiness mailBusiness;
    @Inject private Event<Notification> uiNotificationBusiness;

    @Override
    public Notification process(NotificationTemplate aNotificationTemplate, Map<String, Object> theParametersMap) {
        Notification notification = new Notification();
        notification.setTitle(templateEngineBusiness.process(aNotificationTemplate.getTitle(), theParametersMap));
        notification.setMessage(templateEngineBusiness.process(aNotificationTemplate.getMessage(), theParametersMap));
        return notification;
    }

    @Override
    public void notify(Notification notification, Set<Party> theReceivers,EndPointType...endPointTypes) {
        boolean all = ArrayUtils.contains(endPointTypes, EndPointType.ALL);
        if(all || ArrayUtils.contains(endPointTypes, EndPointType.EMAIL)){
            mailBusiness.send(notification, theReceivers.toArray(new Party[]{}));
        }
        if(all || ArrayUtils.contains(endPointTypes, EndPointType.SMS)){
            
        }
        if(all || ArrayUtils.contains(endPointTypes, EndPointType.UI)){
            uiNotificationBusiness.fire(notification);
        }
        
    }

    @Override
    public void notify(Notification notification, String receiverEmail,EndPointType...endPointTypes) {
        
    }

}
