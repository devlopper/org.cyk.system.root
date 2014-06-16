package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.ScheduleEvent;

public interface EventBusiness extends TypedBusiness<Event> {
    
    Collection<Event> findWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Collection<Event> findWhereFromDateGreaterThanByDate(Date date);
    
    Long countWhereFromDateGreaterThanByDate(Date date);
    
    Collection<ScheduleEvent> process(Event event);
    
    void programAlarm(Collection<Event> events);
    
}
