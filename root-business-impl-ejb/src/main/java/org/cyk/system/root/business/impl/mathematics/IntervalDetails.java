package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class IntervalDetails extends AbstractCollectionItemDetails<Interval> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String low,high;
	
	public IntervalDetails(Interval interval) {
		super(interval);
		if(interval==null){
			
		}else{
			if(interval.getLow()!=null && interval.getLow().getValue()!=null)
				low = formatNumber(interval.getLow().getValue());
			if(interval.getHigh()!=null && interval.getHigh().getValue()!=null)
				high = formatNumber(interval.getHigh().getValue());
		}
	}
	
	public static final String FIELD_LOW = "low";
	public static final String FIELD_HIGH = "high";
	
}