package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class IntervalCollectionDaoImpl extends AbstractCollectionDaoImpl<IntervalCollection,Interval> implements IntervalCollectionDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readLowestValue,readHighestValue;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		//registerNamedQuery(readLowestValue, "SELECT MIN(interval.low.value) FROM Interval interval WHERE interval.collection = :collection");
		//registerNamedQuery(readHighestValue, "SELECT MAX(interval.high.value) FROM Interval interval WHERE interval.collection = :collection");
	}
	
	@Override
	public BigDecimal readLowestValue(IntervalCollection intervalCollection) {
		return namedQuery(readLowestValue, BigDecimal.class).parameter("collection", intervalCollection)
				.ignoreThrowable(NoResultException.class).nullValue(BigDecimal.ZERO).resultOne();
	}

	@Override
	public BigDecimal readHighestValue(IntervalCollection intervalCollection) {
		return namedQuery(readHighestValue, BigDecimal.class).parameter("collection", intervalCollection)
				.ignoreThrowable(NoResultException.class).nullValue(BigDecimal.ZERO).resultOne();
	}
	
}
 