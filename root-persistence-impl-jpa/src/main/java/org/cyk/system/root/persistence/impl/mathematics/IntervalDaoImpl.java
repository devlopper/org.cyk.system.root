package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.utility.common.Constant;

public class IntervalDaoImpl extends AbstractCollectionItemDaoImpl<Interval,IntervalCollection> implements IntervalDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCollectionOrderByFieldName() {
		return Interval.FIELD_LOW+Constant.CHARACTER_DOT+IntervalExtremity.FIELD_VALUE;
	}
	
}
 