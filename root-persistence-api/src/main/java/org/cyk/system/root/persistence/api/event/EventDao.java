package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;

public interface EventDao extends AbstractIdentifiablePeriodDao<Event> {

    Collection<Event> readWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties);
    Long countWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties);
    
    Collection<Event> readByCriteria(EventSearchCriteria criteria);
    Long countByCriteria(EventSearchCriteria criteria);
	
    Collection<Event> readWhereToDateLessThanByDateByParties(Date universalTimeCoordinated, Collection<Party> parties);
	Long countWhereToDateLessThanByDateByParties(Date universalTimeCoordinated,Collection<Party> parties);
	
	Collection<Event> readWhereDateBetweenPeriodByParties(Date universalTimeCoordinated, Collection<Party> parties);
	Long countWhereDateBetweenPeriodByParties(Date universalTimeCoordinated,Collection<Party> parties);
	
	Collection<Event> readWhereFromDateGreaterThanByDateByParties(Date universalTimeCoordinated, Collection<Party> parties);
	Long countWhereFromDateGreaterThanByDateByParties(Date universalTimeCoordinated,Collection<Party> parties);
	
}
