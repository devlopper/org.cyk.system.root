package org.cyk.system.root.business.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.model.time.Period;

public interface RepeatedEventBusiness extends TypedBusiness<RepeatedEvent> {
    
	RepeatedEvent createAnniversary(Integer dayOfMonth,Integer month,String name);
	RepeatedEvent createAnniversary(Date date,String name);
    
	RepeatedEvent updateAnniversary(RepeatedEvent anniversary,Integer dayOfMonth,Integer month,String name);
	RepeatedEvent updateAnniversary(RepeatedEvent anniversary,Date date,String name);
	
	Collection<RepeatedEvent> findByPeriod(Period period);
    Long countByPeriod(Period period);
	
	Collection<RepeatedEvent> findByMonth(Integer month);
    Long countByMonth(Integer month);
	
    Collection<RepeatedEvent> findByDayOfMonth(Integer monthDay);
    Long countByDayOfMonth(Integer dayOfMonth);
    
    Collection<RepeatedEvent> findByDayOfMonthByMonth(Integer monthDay,Integer month);
    Long countByDayOfMonthByMonth(Integer dayOfMonth,Integer month);
}
