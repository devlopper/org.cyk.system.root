package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.persistence.api.event.EventReminderDao;

public class EventReminderDaoImpl extends AbstractIdentifiablePeriodDaoImpl<EventReminder> implements EventReminderDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByEvent,executeDeleteByEvent;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByEvent, _select().where("event"));
		registerNamedQuery(executeDeleteByEvent, "DELETE FROM EventReminder eventReminder WHERE eventReminder.event.identifier = :eventId");
	}
	
	@Override
	public Collection<EventReminder> readByEvent(Event event) {
		return namedQuery(readByEvent).parameter("event", event).resultMany();
	}
	
	@Override
	public void deleteByEvent(Event event) {
		for(EventReminder eventReminder : readByEvent(event))
			delete(eventReminder);
		//namedQuery(deleteByEvent).parameter("eventId", event.getIdentifier()).update();
	}


	
}
 