package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class MetricDetails extends AbstractCollectionItemDetails.AbstractDefault<Metric,MetricCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue valueProperties;
	
	public MetricDetails(Metric metric) {
		super(metric);
	}
	
	@Override
	public void setMaster(Metric metric) {
		super.setMaster(metric);
		if(metric==null){
			
		}else{
			if(metric.getValueProperties()!=null)
				valueProperties = new FieldValue(metric.getValueProperties());
		}
	}
	
	public static final String FIELD_VALUE_PROPERTIES = "valueProperties";
	
}