package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.persistence.api.TypedDao;

public interface RepeatedEventDao extends TypedDao<RepeatedEvent> {

	Collection<RepeatedEvent> readByMonth(Integer month);
    Long countByMonth(Integer month);
    
    Collection<RepeatedEvent> readByMonths(Set<Integer> months);
    Long countByMonths(Set<Integer> months);
	
    Collection<RepeatedEvent> readByDayOfMonth(Integer dayOfMonth);
    Long countByDayOfMonth(Integer dayOfMonth);
    
    Collection<RepeatedEvent> readByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
    Long countByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
    
}
