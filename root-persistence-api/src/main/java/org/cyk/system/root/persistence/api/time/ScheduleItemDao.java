package org.cyk.system.root.persistence.api.time;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface ScheduleItemDao extends AbstractCollectionItemDao<ScheduleItem,Schedule> {

	Collection<ScheduleItem> readWhereFromBetween(Date from,Date to);
	Long countWhereFromBetween(Date from,Date to);
	
	/*
	Collection<ScheduleItem> readByMonth(Integer month);
    Long countByMonth(Integer month);
    
    Collection<ScheduleItem> readByMonths(Set<Integer> months);
    Long countByMonths(Set<Integer> months);
	
    Collection<ScheduleItem> readByDayOfMonth(Integer dayOfMonth);
    Long countByDayOfMonth(Integer dayOfMonth);
    
    Collection<ScheduleItem> readByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
    Long countByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
    */
	
}
