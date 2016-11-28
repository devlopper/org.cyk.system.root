package org.cyk.system.root.business.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValueType;

public interface MetricCollectionBusiness extends AbstractCollectionBusiness<MetricCollection,Metric> {
    
	MetricCollection instanciateOne(String code,String name,MetricCollectionType type,MetricValueType metricValueType,String[] items,String intervalCollectionName,String[][] intervals);
	MetricCollection instanciateOne(String code,String name,MetricCollectionType type,MetricValueType metricValueType,String[] items,String[][] intervals);

	Collection<MetricCollection> findByMetricCollectionIdentifiableGlobalIdentifierSearchCriteria(SearchCriteria searchCriteria);
	Collection<MetricCollection> findByTypesByIdentifiables(Collection<MetricCollectionType> metricCollectionTypes,Collection<? extends AbstractIdentifiable> identifiables);
	Collection<MetricCollection> findByTypesByIdentifiable(Collection<MetricCollectionType> metricCollectionTypes,AbstractIdentifiable identifiable);
    Collection<MetricCollection> findByTypeByIdentifiables(MetricCollectionType metricCollectionType,Collection<? extends AbstractIdentifiable> identifiables);
    Collection<MetricCollection> findByTypeByIdentifiable(MetricCollectionType metricCollectionType,AbstractIdentifiable identifiable);

}
