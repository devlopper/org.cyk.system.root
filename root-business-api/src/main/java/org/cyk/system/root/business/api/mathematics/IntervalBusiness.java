package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;

public interface IntervalBusiness extends TypedBusiness<Interval> {
    
    Interval findByCollectionByValue(IntervalCollection collection,BigDecimal value,Integer scale);
    
}
