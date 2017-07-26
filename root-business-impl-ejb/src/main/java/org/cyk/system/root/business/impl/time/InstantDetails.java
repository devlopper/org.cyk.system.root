package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.time.Instant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class InstantDetails extends AbstractModelElementOutputDetails<Instant> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String year,month,day,dayInWeekIndex,hour,minute,second,millisecond;
	
	public InstantDetails(Instant instant) {
		super(instant);
	}
	
	@Override
	public void setMaster(Instant instant) {
		super.setMaster(instant);
		if(instant==null){
			
		}else{
			set(instant);
		}
	}
	
	public InstantDetails set(Instant instant){
		year = formatNumber(instant.getYear());
		month = formatNumber(instant.getMonth());
		day = formatNumber(instant.getDay());
		dayInWeekIndex = formatNumber(instant.getDayInWeekIndex());
		hour = formatNumber(instant.getHour());
		minute = formatNumber(instant.getMinute());
		second = formatNumber(instant.getSecond());
		millisecond = formatNumber(instant.getMillisecond());
		return this;
	}
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH = "month";
	public static final String FIELD_DAY = "day";
	public static final String FIELD_HOUR = "hour";
	public static final String FIELD_MINUTE = "minute";
	public static final String FIELD_SECOND = "second";
	public static final String FIELD_MILLISECOND = "millisecond";
	public static final String FIELD_DAY_IN_WEEK_INDEX = "dayInWeekIndex";
}
