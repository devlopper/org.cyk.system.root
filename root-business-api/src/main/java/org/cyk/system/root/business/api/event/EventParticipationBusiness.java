package org.cyk.system.root.business.api.event;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.party.Party;

public interface EventParticipationBusiness extends TypedBusiness<EventParticipation> {

	EventParticipation findByEventByParty(Event event,Party party);
	
	Collection<EventParticipation> findByParty(Party party);
	
}
