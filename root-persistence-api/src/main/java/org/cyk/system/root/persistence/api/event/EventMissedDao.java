package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.persistence.api.TypedDao;

public interface EventMissedDao extends TypedDao<EventMissed> {

    Long sumDuration(Date fromDate,Date toDate, Boolean acceptable);
    
    Long sumDuration(Collection<Event> events, Boolean acceptable);
    
    Collection<EventMissed> readByEventParties(Collection<EventParty> eventParties);
    
}
