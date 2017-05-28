package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricValueDetails extends AbstractOutputDetails<MetricValue> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue metric,value;
	
	public MetricValueDetails(MetricValue metricValue) {
		super(metricValue);
	}
	
	@Override
	public void setMaster(MetricValue metricValue) {
		super.setMaster(metricValue);
		if(metricValue!=null){
			metric = new FieldValue(metricValue.getMetric());
			if(metricValue.getValue()!=null)
				value = new FieldValue(metricValue.getValue());
		}
	}
	
	public static final String FIELD_METRIC = "metric";
	public static final String FIELD_VALUE = "value";
	
}