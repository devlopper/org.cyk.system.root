package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.event.EventPartyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class EventPartyDaoImpl extends AbstractTypedDao<EventParty> implements EventPartyDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
    private String readByEvents,readByEventByParty,readByEvent;
    
    @Override
    protected void namedQueriesInitialisation() {
    	super.namedQueriesInitialisation();
        registerNamedQuery(readByEvents, "SELECT participation FROM EventParty participation WHERE participation.event.identifier IN :identifiers");
        registerNamedQuery(readByEventByParty, _select().where("event").and("party"));
        registerNamedQuery(readByEvent, _select().where("event"));
    }
     
	@Override
	public Collection<EventParty> readByEvents(Collection<Event> events) {
		return namedQuery(readByEvents).parameter("identifiers", ids(events))
                .resultMany();
	}

	@Override
	public EventParty readByEventByParty(Event event, Party party) {
		return namedQuery(readByEventByParty).parameter("event", event).parameter("party", party)
				.ignoreThrowable(NoResultException.class)
                .resultOne();
	}

	@Override
	public Collection<EventParty> readByParty(Party party) {
		return null;
	}

	@Override
	public Collection<EventParty> readByEvent(Event event) {
		return namedQuery(readByEvent).parameter("event", event)
				.ignoreThrowable(NoResultException.class)
                .resultMany();
	}

}
 