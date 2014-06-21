package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.AbstractEvent;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractEventDao<EVENT extends AbstractEvent> extends TypedDao<EVENT> {

    Collection<EVENT> readWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate,Date endDate);
    
    Collection<EVENT> readWhereFromDateGreaterThanByDate(Date date);
    
    Long countWhereFromDateGreaterThanByDate(Date date);
}
