package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public class IntervalExtremityDetails extends AbstractModelElementOutputDetails<IntervalExtremity> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String value,excluded;
	
	public IntervalExtremityDetails(IntervalExtremity intervalExtremity) {
		super(intervalExtremity);
	}
	
	@Override
	public void setMaster(IntervalExtremity intervalExtremity) {
		super.setMaster(intervalExtremity);
		if(intervalExtremity==null){
			
		}else{
			set(intervalExtremity);
		}
	}
	
	public IntervalExtremityDetails set(IntervalExtremity intervalExtremity){
		value = formatNumber(intervalExtremity.getValue());
		excluded = formatResponse(intervalExtremity.getExcluded());
		return this;
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EXCLUDED = "excluded";
}
