package org.cyk.system.root.business.api.event;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;

public interface EventMissedBusiness extends TypedBusiness<EventMissed> {
    
	//Long findDuration(Collection<EventCollection> collections,Date fromDate,Date toDate, Boolean acceptable);
    
    Long findDuration(Collection<Event> events, Boolean acceptable);
    
}
