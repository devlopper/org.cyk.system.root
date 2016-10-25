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
	
	@Input @InputText private String lowExtremity,highExtremity;
	
	public IntervalDetails(Interval interval) {
		super(interval);
		if(interval==null){
			
		}else{
			if(interval.getLow()!=null)
				lowExtremity = formatUsingBusiness(interval.getLow());
			if(interval.getHigh()!=null)
				highExtremity = formatUsingBusiness(interval.getHigh());
		}
	}
	
	public static final String FIELD_LOW_EXTREMITY = "lowExtremity";
	public static final String FIELD_HIGH_EXTREMITY = "highExtremity";
	
}