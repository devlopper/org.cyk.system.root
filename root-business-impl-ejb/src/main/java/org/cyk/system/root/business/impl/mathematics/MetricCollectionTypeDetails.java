package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricCollectionTypeDetails extends AbstractOutputDetails<MetricCollectionType> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue collectionValueProperties,metricValueProperties;
	
	public MetricCollectionTypeDetails(MetricCollectionType metricCollectionType) {
		super(metricCollectionType);	
	}
	
	@Override
	public void setMaster(MetricCollectionType metricCollectionType) {
		super.setMaster(metricCollectionType);
		if(metricCollectionType!=null){
			if(metricCollectionType.getCollectionValueProperties()!=null)
				collectionValueProperties = new FieldValue(metricCollectionType.getCollectionValueProperties());
			if(metricCollectionType.getMetricValueProperties()!=null)
				metricValueProperties = new FieldValue(metricCollectionType.getMetricValueProperties());
		}
	}
	
	public static final String FIELD_COLLECTION_VALUE_PROPERTIES = "collectionValueProperties";
	public static final String FIELD_METRIC_VALUE_PROPERTIES = "metricValueProperties";
	
}