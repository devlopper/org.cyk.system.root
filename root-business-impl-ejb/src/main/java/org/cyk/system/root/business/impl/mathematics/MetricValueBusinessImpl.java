package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;

public class MetricValueBusinessImpl extends AbstractTypedBusinessService<MetricValue, MetricValueDao> implements MetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricValueBusinessImpl(MetricValueDao dao) {
		super(dao); 
	}
	
	@Override
	public String format(MetricValue metricValue) {
		String value = null;
		switch(metricValue.getMetric().getCollection().getValueType()){
		case NUMBER:
			value = numberBusiness.format(metricValue.getNumberValue());
			break;
		case STRING:
			value = metricValue.getStringValue();//TODO must depends on string value type
			break;
		}
		return value;
	}
}
