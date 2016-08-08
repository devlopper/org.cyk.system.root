package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.persistence.api.event.EventMissedDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class EventMissedDaoImpl extends AbstractTypedDao<EventMissed> implements EventMissedDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String SUM_DURATION_BY_COLLECTION = "SELECT SUM(em.numberOfMillisecond) FROM EventMissed em WHERE em.eventParty.event.collection.identifier IN :identifiers AND "
			+ "em.eventParty.event.globalIdentifier.existencePeriod.fromDate BETWEEN :fromDate AND :toDate";
	private static final String SUM_DURATION_BY_EVENTS = "SELECT SUM(em.numberOfMillisecond) FROM EventMissed em WHERE em.eventParty.event.identifier IN :identifiers";
	
    private String sumDurationByCollection,sumDurationByCollectionByAcceptable,sumDurationByEvents,sumDurationByEventsByAcceptable,readByEventParties;
    
    @Override
    protected void namedQueriesInitialisation() {
    	super.namedQueriesInitialisation();
        /*registerNamedQuery(sumDurationByCollection, SUM_DURATION_BY_COLLECTION);
        registerNamedQuery(sumDurationByCollectionByAcceptable, SUM_DURATION_BY_COLLECTION+" AND em.reason.acceptable = :acceptable");
        
        registerNamedQuery(sumDurationByEvents, SUM_DURATION_BY_EVENTS);
        registerNamedQuery(sumDurationByEventsByAcceptable, SUM_DURATION_BY_EVENTS+" AND em.reason.acceptable = :acceptable");
        
        registerNamedQuery(readByEventParties, "SELECT em FROM EventMissed em WHERE em.participation.identifier IN :identifiers");
        */
    }
     
	@Override
	public Long sumDuration(Date fromDate,Date toDate, Boolean acceptable) {
		/*if(acceptable==null)
			return sumNamedQuery(sumDurationByCollection).parameter("identifiers", ids(collections)).parameter("fromDate", fromDate).parameter("toDate", toDate)
                .resultOne();
		return sumNamedQuery(sumDurationByCollectionByAcceptable).parameter("identifiers", ids(collections)).parameter("fromDate", fromDate).parameter("toDate", toDate)
				.parameter("acceptable", acceptable)
                .resultOne();*/
		return null;
	}

	@Override
	public Long sumDuration(Collection<Event> events, Boolean acceptable) {
		if(acceptable==null)
			return sumNamedQuery(sumDurationByEvents).parameter("identifiers", ids(events))
                .resultOne();
		return sumNamedQuery(sumDurationByEventsByAcceptable).parameter("identifiers", ids(events)).parameter("acceptable", acceptable)
                .resultOne();
	}

	@Override
	public Collection<EventMissed> readByEventParties(Collection<EventParty> eventParties) {
		return namedQuery(readByEventParties).parameter("identifiers", ids(eventParties))
                .resultMany();
	}

}
 