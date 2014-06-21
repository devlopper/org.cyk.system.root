package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.AbstractEvent;

public interface AbstractEventBusiness<EVENT extends AbstractEvent> extends TypedBusiness<EVENT> {
    
    Collection<EVENT> findWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Collection<EVENT> findWhereFromDateGreaterThanByDate(Date date);
    
    Long countWhereFromDateGreaterThanByDate(Date date);
    
    void programAlarm(Collection<EVENT> events);
    
}
