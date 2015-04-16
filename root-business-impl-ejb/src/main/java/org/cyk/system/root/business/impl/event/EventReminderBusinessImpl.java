package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventReminderBusiness;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.persistence.api.event.EventReminderDao;

public class EventReminderBusinessImpl extends AbstractIdentifiablePeriodBusinessImpl<EventReminder, EventReminderDao> implements EventReminderBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public EventReminderBusinessImpl(EventReminderDao dao) {
		super(dao); 
	}  
    
    
}
