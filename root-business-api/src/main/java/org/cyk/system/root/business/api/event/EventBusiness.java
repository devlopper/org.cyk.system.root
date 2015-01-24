package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Event;

public interface EventBusiness extends TypedBusiness<Event> {
    
    Collection<Event> findWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Collection<Event> findWhereFromDateGreaterThanByDate(Date date);
    
    Long countWhereFromDateGreaterThanByDate(Date date);
    
    //TODO instead of using thread living in memory we can use Scaaning the database and notify. Yes it might not be accurate....
    void programAlarm(Collection<Event> events);
    
    Long findDuration(Collection<Event> events);
    
    /*
     * Transactions
     */
    
    void create(Collection<Event> events);
    
}
