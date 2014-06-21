package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.persistence.api.TypedDao;

public interface EventDao extends TypedDao<Event> {

    Collection<Event> readWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Collection<Event> readWhereFromDateGreaterThanByDate(Date date);
    
    Long countWhereFromDateGreaterThanByDate(Date date);
    
}
