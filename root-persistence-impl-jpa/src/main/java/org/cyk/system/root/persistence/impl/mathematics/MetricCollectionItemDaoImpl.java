package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionItemDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public class MetricCollectionItemDaoImpl extends AbstractCollectionItemDaoImpl<Metric,MetricCollection> implements MetricCollectionItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 