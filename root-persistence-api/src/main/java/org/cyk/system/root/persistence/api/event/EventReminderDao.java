package org.cyk.system.root.persistence.api.event;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventReminder;

public interface EventReminderDao extends AbstractIdentifiablePeriodDao<EventReminder> {

	Collection<EventReminder> readByEvent(Event event);
	void deleteByEvent(Event event);

    
}
