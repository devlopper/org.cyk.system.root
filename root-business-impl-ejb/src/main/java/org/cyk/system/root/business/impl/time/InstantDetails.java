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

	@Input @InputText private String year,monthOfYear,dayOfMonth,dayOfWeek,hourOfDay,minuteOfHour,secondOfMinute,millisecondOfSecond;
	
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
		monthOfYear = formatNumber(instant.getMonthOfYear());
		dayOfMonth = formatNumber(instant.getDayOfMonth());
		dayOfWeek = formatNumber(instant.getDayOfWeek());
		hourOfDay = formatNumber(instant.getHourOfDay());
		minuteOfHour = formatNumber(instant.getMinuteOfHour());
		secondOfMinute = formatNumber(instant.getSecondOfMinute());
		millisecondOfSecond = formatNumber(instant.getMillisecondOfSecond());
		return this;
	}
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH_OF_YEAR = "monthOfYear";
	public static final String FIELD_DAY_OF_MONTH = "dayOfMonth";
	public static final String FIELD_DAY_OF_WEEK = "dayOfWeek";
	public static final String FIELD_HOUR_OF_DAY = "hourOfDay";
	public static final String FIELD_MINUTE_OF_HOUR = "minuteOfHour";
	public static final String FIELD_SECOND_OF_MINUTE = "secondOfMinute";
	public static final String FIELD_MILLISECOND_OF_SECOND = "millisecondOfSecond";
	
}
