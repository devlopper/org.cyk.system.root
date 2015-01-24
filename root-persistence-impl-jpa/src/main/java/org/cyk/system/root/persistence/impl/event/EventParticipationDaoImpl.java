package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.persistence.api.event.EventParticipationDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class EventParticipationDaoImpl extends AbstractTypedDao<EventParticipation> implements EventParticipationDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
    private String readByEvents;
    
    @Override
    protected void namedQueriesInitialisation() {
    	super.namedQueriesInitialisation();
        registerNamedQuery(readByEvents, "SELECT participation FROM EventParticipation participation WHERE participation.event.identifier IN :identifiers");
        
    }
     
	@Override
	public Collection<EventParticipation> readByEvents(Collection<Event> events) {
		return namedQuery(readByEvents).parameter("identifiers", ids(events))
                .resultMany();
	}

}
 