package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.time.AbstractIdentifiablePeriodBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Event.SearchCriteria;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.helper.EventHelper;

public interface EventBusiness extends AbstractIdentifiablePeriodBusiness<Event> {
    
    Collection<Event> findWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties);
    Long countWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties);
    
    Collection<Event> findByCriteria(SearchCriteria criteria);
    Long countByCriteria(SearchCriteria criteria);
    
    Collection<Event> findPasts(Collection<Party> parties);
    Long countPasts(Collection<Party> parties);
    
    Collection<Event> findCurrents(Collection<Party> parties);
    Long countCurrents(Collection<Party> parties);
    
    Collection<Event> findOnComings(Collection<Party> parties);
    Long countOnComings(Collection<Party> parties);
    
    Collection<EventHelper.Event> findEvents(Date from,Date to);
    
}
