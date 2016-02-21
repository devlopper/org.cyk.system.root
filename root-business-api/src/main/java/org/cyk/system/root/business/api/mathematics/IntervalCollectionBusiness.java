package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;

public interface IntervalCollectionBusiness extends AbstractCollectionBusiness<IntervalCollection,Interval> {
    
    BigDecimal findLowestValue(IntervalCollection intervalCollection);
    BigDecimal findHighestValue(IntervalCollection intervalCollection);
    
    Boolean isAllIntervalLowerEqualsToHigher(IntervalCollection intervalCollection); 
}
