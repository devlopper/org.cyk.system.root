package org.cyk.system.root.persistence.api.event;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.TypedDao;

public interface EventPartyDao extends TypedDao<EventParty> {

    Collection<EventParty> readByEvents(Collection<Event> events);

	EventParty readByEventByParty(Event event, Party party);

	Collection<EventParty> readByParty(Party party);

	Collection<EventParty> readByEvent(Event event);
    
}
