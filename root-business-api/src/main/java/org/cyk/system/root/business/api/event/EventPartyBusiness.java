package org.cyk.system.root.business.api.event;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.Party;

public interface EventPartyBusiness extends TypedBusiness<EventParty> {

	EventParty findByEventByParty(Event event,Party party);
	
	Collection<EventParty> findByParty(Party party);

	Collection<EventParty> findByEvent(Event event);

	void setMissedEvent(Collection<EventParty> eventParties);
	
}
