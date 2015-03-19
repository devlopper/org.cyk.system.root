package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.time.Period;

public interface EventBusiness extends TypedBusiness<Event> {
    
    Collection<Event> findWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Collection<Event> findWhereFromDateGreaterThanByDate(Date date);
    
    Long countWhereFromDateGreaterThanByDate(Date date);
    
    Collection<Event> findToAlarm();
       
    Collection<Event> findWhereAlarmFromDateBetween(Period period);
    
    Long countWhereAlarmFromDateBetween(Period period);
    
    Collection<Event> findWhereDateBetweenAlarmPeriod(Date date);
    
    Long countWhereDateBetweenAlarmPeriod(Date date);
    
    Long findDuration(Collection<Event> events);
    
    Collection<Event> findByCriteria(EventSearchCriteria criteria);
    
    Long countByCriteria(EventSearchCriteria criteria);
    
    /*
     * Transactions
     */
    
    void create(Collection<Event> events);
    
    //void disableAlarm(Collection<Event> events);
    
}
