package org.cyk.system.root.business.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;

public interface MetricValueBusiness extends TypedBusiness<MetricValue> {

	Collection<MetricValue> findByMetrics(Collection<Metric> metrics);
	
	Collection<MetricValue> findByMetricsByIdentifiables(Collection<Metric> metrics,Collection<? extends AbstractIdentifiable> identifiables);
	
	void updateManyRandomly(Collection<String> metricCollectionCodes,Collection<? extends AbstractIdentifiable> metricCollectionIdentifiables
			,Collection<? extends AbstractIdentifiable> metricValueIdentifiables);
	
}
