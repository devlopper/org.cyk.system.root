package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class MetricCollectionDetails extends AbstractCollectionDetails.Extends<MetricCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue type,valueProperties,value;
	
	public MetricCollectionDetails(MetricCollection metricCollection) {
		super(metricCollection);
	}
	
	@Override
	public void setMaster(MetricCollection metricCollection) {
		super.setMaster(metricCollection);
		if(metricCollection!=null){
			if(metricCollection.getType()!=null)
				type = new FieldValue(metricCollection.getType());
			if(metricCollection.getValueProperties()!=null)
				valueProperties = new FieldValue(metricCollection.getValueProperties());
			if(metricCollection.getValue()!=null)
				value = new FieldValue(metricCollection.getValue());
		}
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_VALUE_PROPERTIES = "valueProperties";
	public static final String FIELD_VALUE = "value";

}