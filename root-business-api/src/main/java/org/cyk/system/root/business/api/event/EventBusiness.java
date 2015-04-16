package org.cyk.system.root.business.api.event;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;

public interface EventBusiness extends AbstractIdentifiablePeriodBusiness<Event> {
    
    Collection<Event> findWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties);
    Long countWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties);
    
    Collection<Event> findByCriteria(EventSearchCriteria criteria);
    Long countByCriteria(EventSearchCriteria criteria);
    
    void create(Event event,Collection<EventReminder> eventReminders);
    
    Collection<Event> findPasts(Collection<Party> parties);
    Long countPasts(Collection<Party> parties);
    
    Collection<Event> findCurrents(Collection<Party> parties);
    Long countCurrents(Collection<Party> parties);
    
    Collection<Event> findOnComings(Collection<Party> parties);
    Long countOnComings(Collection<Party> parties);
}
