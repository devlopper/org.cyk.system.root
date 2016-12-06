package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.persistence.api.TypedDao;

public interface MetricValueDao extends TypedDao<MetricValue> {

	Collection<MetricValue> readByMetrics(Collection<Metric> metrics);
    
}
