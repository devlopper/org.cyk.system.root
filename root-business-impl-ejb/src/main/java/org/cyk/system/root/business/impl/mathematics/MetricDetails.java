package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class MetricDetails extends AbstractCollectionItemDetails<Metric> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String valueIntervalCollection;
	
	public MetricDetails(Metric metric) {
		super(metric);
		if(metric==null){
			
		}else{
			if(metric.getValueIntervalCollection()!=null)
				valueIntervalCollection = formatUsingBusiness(metric.getValueIntervalCollection());
		}
	}
	
	public static final String FIELD_VALUE_INTERVAL_COLLECTION = "valueIntervalCollection";
	
}