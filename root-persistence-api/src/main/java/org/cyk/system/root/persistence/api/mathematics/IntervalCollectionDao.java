package org.cyk.system.root.persistence.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public interface IntervalCollectionDao extends AbstractCollectionDao<IntervalCollection,Interval> {

	BigDecimal readLowestValue(IntervalCollection intervalCollection);
    BigDecimal readHighestValue(IntervalCollection intervalCollection);
	
}
