package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.EventRepetition;
import org.cyk.system.root.model.time.Period;

@Deprecated
public interface RepeatedEventBusiness extends TypedBusiness<EventRepetition> {
    
	EventRepetition createAnniversary(Integer dayOfMonth,Integer month,String name);
	EventRepetition createAnniversary(Date date,String name);
    
	EventRepetition updateAnniversary(EventRepetition anniversary,Integer dayOfMonth,Integer month,String name);
	EventRepetition updateAnniversary(EventRepetition anniversary,Date date,String name);
	
	Collection<EventRepetition> findByPeriod(Period period);
    Long countByPeriod(Period period);
	
	Collection<EventRepetition> findByMonth(Integer month);
    Long countByMonth(Integer month);
	
    Collection<EventRepetition> findByDayOfMonth(Integer monthDay);
    Long countByDayOfMonth(Integer dayOfMonth);
    
    Collection<EventRepetition> findByDayOfMonthByMonth(Integer monthDay,Integer month);
    Long countByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
}
