package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IntervalDetails extends AbstractCollectionItemDetails.AbstractDefault<Interval,IntervalCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String lowExtremity,highExtremity;
	
	public IntervalDetails(Interval interval) {
		super(interval);
	}
	
	@Override
	public void setMaster(Interval interval) {
		super.setMaster(interval);
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