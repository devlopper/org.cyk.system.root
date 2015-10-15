package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public class MetricDaoImpl extends AbstractCollectionItemDaoImpl<Metric,MetricCollection> implements MetricDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 