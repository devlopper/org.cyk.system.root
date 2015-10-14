package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;

public interface IntervalBusiness extends AbstractEnumerationBusiness<Interval> {
    
    Interval findByCollectionByValue(IntervalCollection collection,BigDecimal value,Integer scale);
    
    Collection<Interval> findByCollection(IntervalCollection collection);
}
