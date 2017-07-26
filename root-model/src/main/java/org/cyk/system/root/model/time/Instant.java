package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class Instant extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;

	@Column(name=COLUMN_YEAR)				private Short year;
	@Column(name=COLUMN_MONTH)				private Byte month;
	@Column(name=COLUMN_DAY)				private Byte day;
	@Column(name=COLUMN_DAY_IN_WEEK_INDEX)	private Byte dayInWeekIndex;
	@Column(name=COLUMN_HOUR)				private Byte hour;
	@Column(name=COLUMN_MINUTE)				private Byte minute;
	@Column(name=COLUMN_SECOND)				private Byte second;	
	@Column(name=COLUMN_MILLISECOND)		private Short millisecond;
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return hour+" "+minute+" "+second+" "+millisecond+" "+day+" "+month+" "+year;
	}
	
	/**/
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH = "month";
	public static final String FIELD_DAY = "day";
	public static final String FIELD_HOUR = "hour";
	public static final String FIELD_MINUTE = "minute";
	public static final String FIELD_SECOND = "second";
	public static final String FIELD_MILLISECOND = "millisecond";
	public static final String FIELD_DAY_IN_WEEK_INDEX = "dayInWeekIndex";
	
	public static final String COLUMN_PREFIX = "instant_";
	public static final String COLUMN_YEAR = COLUMN_PREFIX+FIELD_YEAR;
	public static final String COLUMN_MONTH = COLUMN_PREFIX+FIELD_MONTH;
	public static final String COLUMN_DAY = COLUMN_PREFIX+FIELD_DAY;
	public static final String COLUMN_HOUR = COLUMN_PREFIX+FIELD_HOUR;
	public static final String COLUMN_MINUTE = COLUMN_PREFIX+FIELD_MINUTE;
	public static final String COLUMN_SECOND = COLUMN_PREFIX+FIELD_SECOND;
	public static final String COLUMN_MILLISECOND = COLUMN_PREFIX+FIELD_MILLISECOND;
	public static final String COLUMN_DAY_IN_WEEK_INDEX = COLUMN_PREFIX+FIELD_DAY_IN_WEEK_INDEX;
}
