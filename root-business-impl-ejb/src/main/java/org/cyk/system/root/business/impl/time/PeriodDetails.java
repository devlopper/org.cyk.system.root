package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PeriodDetails extends AbstractModelElementOutputDetails<Period> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String fromDate,toDate;
	
	public PeriodDetails(Period period) {
		super(period);
	}
	
	@Override
	public void setMaster(Period period) {
		super.setMaster(period);
		if(period==null){
			
		}else{
			set(period);
		}
	}
	
	public PeriodDetails set(Period period){
		fromDate = formatDateTime(period.getFromDate());
		toDate = formatDateTime(period.getToDate());
		return this;
	}
	
	public static final String FIELD_FROM_DATE = "fromDate";
	public static final String FIELD_TO_DATE = "toDate";
}
