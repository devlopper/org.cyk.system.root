package org.cyk.system.root.business.api.mathematics;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValueType;

public interface MetricBusiness extends AbstractCollectionItemBusiness<Metric,MetricCollection> {

	MetricValueType getValueType(Metric metric);
    
}
