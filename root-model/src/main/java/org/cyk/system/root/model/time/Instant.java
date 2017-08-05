package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.ByteSearchCriteria;
import org.cyk.system.root.model.search.ShortSearchCriteria;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.helper.NumberHelper;
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
	
	@Column(name=COLUMN_DATE) @Temporal(TemporalType.TIMESTAMP)	private Date date;
	private @Transient Boolean synchronizeDate = Boolean.TRUE;
	
	public TimeHelper.Instant getTimeHelperInstant(){
		return new TimeHelper.Instant(year, monthOfYear, dayOfMonth, dayOfWeek, hourOfDay, minuteOfHour, secondOfMinute, millisecondOfSecond);
	}
	
	public Instant set(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer dayOfWeek,Integer hourOfDay,Integer minuteOfHour,Integer secondOfMinute,Integer millisecondOfSecond){
		setYear(NumberHelper.getInstance().get(Short.class, year));
		setMonthOfYear(NumberHelper.getInstance().get(Byte.class, monthOfYear));
		setDayOfMonth(NumberHelper.getInstance().get(Byte.class, dayOfMonth));
		setDayOfWeek(NumberHelper.getInstance().get(Byte.class, dayOfWeek));
		setHourOfDay(NumberHelper.getInstance().get(Byte.class, hourOfDay));
		setMinuteOfHour(NumberHelper.getInstance().get(Byte.class, minuteOfHour));
		setSecondOfMinute(NumberHelper.getInstance().get(Byte.class, secondOfMinute));
		setMillisecondOfSecond(NumberHelper.getInstance().get(Short.class, millisecondOfSecond));
		return this;
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
	public static final String FIELD_DATE = "date";
	
	public static final String COLUMN_PREFIX = "instant_";
	public static final String COLUMN_YEAR = COLUMN_PREFIX+FIELD_YEAR;
	public static final String COLUMN_MONTH_OF_YEAR = COLUMN_PREFIX+FIELD_MONTH_OF_YEAR;
	public static final String COLUMN_DAY_OF_MONTH = COLUMN_PREFIX+FIELD_DAY_OF_MONTH;
	public static final String COLUMN_DAY_OF_WEEK = COLUMN_PREFIX+FIELD_DAY_OF_WEEK;
	public static final String COLUMN_HOUR_OF_DAY = COLUMN_PREFIX+FIELD_HOUR_OF_DAY;
	public static final String COLUMN_MINUTE_OF_HOUR = COLUMN_PREFIX+FIELD_MINUTE_OF_HOUR;
	public static final String COLUMN_SECOND_OF_MINUTE = COLUMN_PREFIX+FIELD_SECOND_OF_MINUTE;
	public static final String COLUMN_MILLISECOND_OF_SECOND = COLUMN_PREFIX+FIELD_MILLISECOND_OF_SECOND;
	public static final String COLUMN_DATE = COLUMN_PREFIX+FIELD_DATE;
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		private ShortSearchCriteria year = new ShortSearchCriteria();
		private ByteSearchCriteria monthOfYear = new ByteSearchCriteria();
		private ByteSearchCriteria dayOfMonth = new ByteSearchCriteria();
		private ByteSearchCriteria dayOfWeek = new ByteSearchCriteria();
		private ByteSearchCriteria hourOfDay = new ByteSearchCriteria();
		private ByteSearchCriteria minuteOfHour = new ByteSearchCriteria();
		private ByteSearchCriteria secondOfMinute = new ByteSearchCriteria();
		private ShortSearchCriteria millisecondOfSecond = new ShortSearchCriteria();
		//private LongSearchCriteria millisecond = new LongSearchCriteria();

		@Override
		public void set(String value) {
			
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
		public SearchCriteria set(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer dayOfWeek,Integer hourOfDay,Integer minuteOfHour,Integer secondOfMinute,Integer millisecondOfSecond){
			getYear().setValue(NumberHelper.getInstance().get(Short.class, year));
			getMonthOfYear().setValue(NumberHelper.getInstance().get(Byte.class, monthOfYear));
			getDayOfMonth().setValue(NumberHelper.getInstance().get(Byte.class, dayOfMonth));
			getDayOfWeek().setValue(NumberHelper.getInstance().get(Byte.class, dayOfWeek));
			getHourOfDay().setValue(NumberHelper.getInstance().get(Byte.class, hourOfDay));
			getMinuteOfHour().setValue(NumberHelper.getInstance().get(Byte.class, minuteOfHour));
			getSecondOfMinute().setValue(NumberHelper.getInstance().get(Byte.class, secondOfMinute));
			getMillisecondOfSecond().setValue(NumberHelper.getInstance().get(Short.class, millisecondOfSecond));
			return this;
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
