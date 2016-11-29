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
	
	@Input @InputText private String value;
	
	public MetricValueDetails(MetricValue metricValue) {
		super(metricValue);
		value = formatUsingBusiness(metricValue);
	}
	
	public static final String FIELD_VALUE = "value";
	
}