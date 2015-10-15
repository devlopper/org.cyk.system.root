package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class IntervalCollectionDaoImpl extends AbstractCollectionDaoImpl<IntervalCollection,Interval> implements IntervalCollectionDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
}
 