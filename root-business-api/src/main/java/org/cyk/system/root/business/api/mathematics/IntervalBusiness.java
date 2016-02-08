package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;

public interface IntervalBusiness extends AbstractCollectionItemBusiness<Interval,IntervalCollection> {
    
    Interval findByCollectionByValue(IntervalCollection collection,BigDecimal value,Integer scale);
    
    Interval instanciateOne(IntervalCollection collection,String code,String low,String high);
    
    Boolean isLower(Interval interval, BigDecimal value,Integer scale);
    Boolean isHigher(Interval interval, BigDecimal value,Integer scale);
    Boolean contains(Interval interval, BigDecimal value,Integer scale);
    
    BigDecimal findLowestGreatestValue(Interval interval);
    BigDecimal findGreatestLowestValue(Interval interval);
}
