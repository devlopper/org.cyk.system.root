package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public interface MetricCollectionDao extends AbstractCollectionDao<MetricCollection,Metric> {

    Collection<MetricCollection> readByTypes(Collection<MetricCollectionType> types);
	
}
