package org.cyk.system.root.persistence.api.event;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.AbstractIdentifiablePeriod;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractIdentifiablePeriodDao<IDENTIFIABLE extends AbstractIdentifiablePeriod> extends TypedDao<IDENTIFIABLE> {

    Collection<IDENTIFIABLE> readWhereFromDateBetweenPeriod(Period period);
    Long countWhereFromDateBetweenPeriod(Period period);
     
    Collection<IDENTIFIABLE> readWhereFromDateGreaterThanByDate(Date date);
    Long countWhereFromDateGreaterThanByDate(Date date);
    
    Collection<IDENTIFIABLE> readWhereToDateLessThanByDate(Date date);
    Long countWhereToDateLessThanByDate(Date date);
    
    Collection<IDENTIFIABLE> readWhereDateBetweenPeriod(Date date);
    Long countWhereDateBetweenPeriod(Date date);
    
}
