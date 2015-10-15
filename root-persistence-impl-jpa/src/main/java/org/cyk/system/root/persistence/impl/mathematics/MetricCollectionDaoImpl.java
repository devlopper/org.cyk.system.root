package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class MetricCollectionDaoImpl extends AbstractCollectionDaoImpl<MetricCollection,Metric> implements MetricCollectionDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 