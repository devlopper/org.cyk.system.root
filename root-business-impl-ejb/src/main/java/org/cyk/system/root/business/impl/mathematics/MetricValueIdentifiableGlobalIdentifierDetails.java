package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricValueIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<MetricValueIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText
	private String metric,value;
	
	public MetricValueIdentifiableGlobalIdentifierDetails(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier) {
		super(metricValueIdentifiableGlobalIdentifier);
		metric = formatUsingBusiness(metricValueIdentifiableGlobalIdentifier.getMetricValue().getMetric());
		value = formatUsingBusiness(metricValueIdentifiableGlobalIdentifier.getMetricValue().getValue());
	}
	
	public static final String FIELD_METRIC = "metric";
	public static final String FIELD_VALUE = "value";
	
}