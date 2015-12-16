package org.cyk.system.root.business.api.mathematics;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.mathematics.MetricValue;

public interface MetricValueBusiness extends TypedBusiness<MetricValue> {
    
	String format(MetricValue metricValue);
    
}
