package org.cyk.system.root.business.api.event;

import java.util.Map;
import java.util.Set;

import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.party.Party;

public interface NotificationBusiness {
	
    public enum EndPointType{ALL,UI,EMAIL,SMS}
    
	public Notification process(NotificationTemplate aNotificationTemplate,Map<String,Object> theParametersMap);
	
	public void notify(Notification notification,Set<Party> receiver,EndPointType...endPointTypes);
	
	public void notify(Notification notification,String receiverEmail,EndPointType...endPointTypes);

}