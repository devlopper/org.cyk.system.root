package org.cyk.system.root.business.api.time;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.time.AbstractIdentifiablePeriod;
import org.cyk.system.root.model.time.Period;

public interface AbstractIdentifiablePeriodBusiness<IDENTIFIABLE extends AbstractIdentifiablePeriod> extends TypedBusiness<IDENTIFIABLE> {
    
	Collection<IDENTIFIABLE> findWhereFromDateBetweenPeriod(Period period);
    Long countWhereFromDateBetweenPeriod(Period period);
     
    Collection<IDENTIFIABLE> findWhereFromDateGreaterThanByDate(Date date);
    Long countWhereFromDateGreaterThanByDate(Date date);
    
    Collection<IDENTIFIABLE> findWhereToDateLessThanByDate(Date date);
    Long countWhereToDateLessThanByDate(Date date);
    
    Collection<IDENTIFIABLE> findWhereDateBetweenPeriod(Date date);
    Long countWhereDateBetweenPeriod(Date date);
    
    Long findDuration(Collection<IDENTIFIABLE> identifiables);
    
    /**/
    
    Collection<IDENTIFIABLE> findPasts();
    Long countPasts();
    
    Collection<IDENTIFIABLE> findCurrents();
    Long countCurrents();
    
    Collection<IDENTIFIABLE> findOnComings();
    Long countOnComings();
    
}
