package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.model.event.EventRepetition;
import org.cyk.system.root.persistence.api.TypedDao;

public interface RepeatedEventDao extends TypedDao<EventRepetition> {

	Collection<EventRepetition> readByMonth(Integer month);
    Long countByMonth(Integer month);
    
    Collection<EventRepetition> readByMonths(Set<Integer> months);
    Long countByMonths(Set<Integer> months);
	
    Collection<EventRepetition> readByDayOfMonth(Integer dayOfMonth);
    Long countByDayOfMonth(Integer dayOfMonth);
    
    Collection<EventRepetition> readByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
    Long countByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
    
}
