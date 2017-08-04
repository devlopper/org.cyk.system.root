package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricCollectionIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<MetricCollectionIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText
	private String metricCollection;
	
	public MetricCollectionIdentifiableGlobalIdentifierDetails(MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier) {
		super(metricCollectionIdentifiableGlobalIdentifier);
		metricCollection = formatUsingBusiness(metricCollectionIdentifiableGlobalIdentifier.getMetricCollection());
	}
	
	public static final String FIELD_METRIC_COLLECTION = "metricCollection";
	
}