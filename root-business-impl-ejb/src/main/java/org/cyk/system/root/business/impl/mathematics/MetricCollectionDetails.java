package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class MetricCollectionDetails extends AbstractCollectionDetails.Extends<MetricCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String type,valueProperties;
	
	public MetricCollectionDetails(MetricCollection metricCollection) {
		super(metricCollection);
		type = formatUsingBusiness(metricCollection.getType());
		valueProperties = formatUsingBusiness(metricCollection.getValueProperties());
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_VALUE_PROPERTIES = "valueProperties";

}