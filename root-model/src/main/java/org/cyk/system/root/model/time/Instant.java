package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Embeddable @Getter @Setter @Accessors(chain=true)
public class Instant extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;

	@Column(name=COLUMN_YEAR)					private Short year;
	@Column(name=COLUMN_MONTH_OF_YEAR)			private Byte monthOfYear;
	@Column(name=COLUMN_DAY_OF_MONTH)			private Byte dayOfMonth;
	@Column(name=COLUMN_DAY_OF_WEEK)			private Byte dayOfWeek;
	@Column(name=COLUMN_HOUR_OF_DAY)			private Byte hourOfDay;
	@Column(name=COLUMN_MINUTE_OF_HOUR)			private Byte minuteOfHour;
	@Column(name=COLUMN_SECOND_OF_MINUTE)		private Byte secondOfMinute;
	@Column(name=COLUMN_MILLISECOND_OF_SECOND)	private Short millisecondOfSecond;
	
	public TimeHelper.Instant getTimeHelperInstant(){
		return new TimeHelper.Instant(year, monthOfYear, dayOfMonth, dayOfWeek, hourOfDay, minuteOfHour, secondOfMinute, millisecondOfSecond);
	}
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return new TimeHelper.Stringifier.Unit.Adapter.Default()
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, year).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, monthOfYear)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, dayOfMonth).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, hourOfDay)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, minuteOfHour).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, secondOfMinute)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, millisecondOfSecond)
		.execute();
	}
	
	/**/
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH_OF_YEAR = "monthOfYear";
	public static final String FIELD_DAY_OF_MONTH = "dayOfMonth";
	public static final String FIELD_DAY_OF_WEEK = "dayOfWeek";
	public static final String FIELD_HOUR_OF_DAY = "hourOfDay";
	public static final String FIELD_MINUTE_OF_HOUR = "minuteOfHour";
	public static final String FIELD_SECOND_OF_MINUTE = "secondOfMinute";
	public static final String FIELD_MILLISECOND_OF_SECOND = "millisecondOfSecond";
	
	
	public static final String COLUMN_PREFIX = "instant_";
	public static final String COLUMN_YEAR = COLUMN_PREFIX+FIELD_YEAR;
	public static final String COLUMN_MONTH_OF_YEAR = COLUMN_PREFIX+FIELD_MONTH_OF_YEAR;
	public static final String COLUMN_DAY_OF_MONTH = COLUMN_PREFIX+FIELD_DAY_OF_MONTH;
	public static final String COLUMN_DAY_OF_WEEK = COLUMN_PREFIX+FIELD_DAY_OF_WEEK;
	public static final String COLUMN_HOUR_OF_DAY = COLUMN_PREFIX+FIELD_HOUR_OF_DAY;
	public static final String COLUMN_MINUTE_OF_HOUR = COLUMN_PREFIX+FIELD_MINUTE_OF_HOUR;
	public static final String COLUMN_SECOND_OF_MINUTE = COLUMN_PREFIX+FIELD_SECOND_OF_MINUTE;
	public static final String COLUMN_MILLISECOND_OF_SECOND = COLUMN_PREFIX+FIELD_MILLISECOND_OF_SECOND;
	
}
