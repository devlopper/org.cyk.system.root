package org.cyk.system.root.business.api.mathematics;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValueType;

public interface MetricCollectionBusiness extends AbstractCollectionBusiness<MetricCollection,Metric> {
    
	MetricCollection instanciateOne(String code,String name,MetricValueType metricValueType,String[] items,String[][] intervals);
    
}
