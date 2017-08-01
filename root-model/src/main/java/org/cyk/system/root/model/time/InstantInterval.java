package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

/**
 * A period is a time interval from a start date to an end date
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InstantInterval extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;

	@Embedded 
	@AttributeOverrides(value={
			@AttributeOverride(name=Instant.FIELD_MILLISECOND,column=@Column(name=COLUMN_FROM_MILLISECOND))
			,@AttributeOverride(name=Instant.FIELD_SECOND,column=@Column(name=COLUMN_FROM_SECOND))
			,@AttributeOverride(name=Instant.FIELD_MINUTE,column=@Column(name=COLUMN_FROM_MINUTE))
			,@AttributeOverride(name=Instant.FIELD_HOUR,column=@Column(name=COLUMN_FROM_HOUR))
			,@AttributeOverride(name=Instant.FIELD_DAY,column=@Column(name=COLUMN_FROM_DAY))
			,@AttributeOverride(name=Instant.FIELD_DAY_IN_WEEK_INDEX,column=@Column(name=COLUMN_FROM_WEEK_DAY_INDEX))
			,@AttributeOverride(name=Instant.FIELD_MONTH,column=@Column(name=COLUMN_FROM_MONTH))
			,@AttributeOverride(name=Instant.FIELD_YEAR,column=@Column(name=COLUMN_FROM_YEAR))
	})
	private Instant from;
	
	@Embedded 
	@AttributeOverrides(value={
			@AttributeOverride(name=Instant.FIELD_MILLISECOND,column=@Column(name=COLUMN_TO_MILLISECOND))
			,@AttributeOverride(name=Instant.FIELD_SECOND,column=@Column(name=COLUMN_TO_SECOND))
			,@AttributeOverride(name=Instant.FIELD_MINUTE,column=@Column(name=COLUMN_TO_MINUTE))
			,@AttributeOverride(name=Instant.FIELD_HOUR,column=@Column(name=COLUMN_TO_HOUR))
			,@AttributeOverride(name=Instant.FIELD_DAY,column=@Column(name=COLUMN_TO_DAY))
			,@AttributeOverride(name=Instant.FIELD_DAY_IN_WEEK_INDEX,column=@Column(name=COLUMN_TO_WEEK_DAY_INDEX))
			,@AttributeOverride(name=Instant.FIELD_MONTH,column=@Column(name=COLUMN_TO_MONTH))
			,@AttributeOverride(name=Instant.FIELD_YEAR,column=@Column(name=COLUMN_TO_YEAR))
	})
	private Instant to;
	
	@Column(name=COLUMN_DISTANCE_IN_MILLISECOND) private Long distanceInMillisecond;
	
	@Column(name=COLUMN_DURATION_IN_MILLISECOND) private Long durationInMillisecond;
	
	{
		if(from==null)
			from = new Instant();
		if(to==null)
			to = new Instant();
	}
	
	public Instant getFrom(){
		if(from==null)
			from = new Instant();
		return from;
	}
	
	public Instant getTo(){
		if(to==null)
			to = new Instant();
		return to;
	}
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return getFrom().getUiString()+" to "+getTo().getUiString();
	}
	
	/**/
	
	public static final String FIELD_FROM = "from";
	public static final String FIELD_TO = "to";
	public static final String FIELD_DISTANCE_IN_MILLISECOND = "distanceInMillisecond";
	public static final String FIELD_DURATION_IN_MILLISECOND = "durationInMillisecond";
	
	private static final String COLUMN_FROM_PREFIX = "from_";
	public static final String COLUMN_FROM_YEAR = COLUMN_FROM_PREFIX+Instant.COLUMN_YEAR;
	public static final String COLUMN_FROM_MONTH = COLUMN_FROM_PREFIX+Instant.COLUMN_MONTH;
	public static final String COLUMN_FROM_DAY = COLUMN_FROM_PREFIX+Instant.COLUMN_DAY;
	public static final String COLUMN_FROM_HOUR = COLUMN_FROM_PREFIX+Instant.COLUMN_HOUR;
	public static final String COLUMN_FROM_MINUTE = COLUMN_FROM_PREFIX+Instant.COLUMN_MINUTE;
	public static final String COLUMN_FROM_SECOND = COLUMN_FROM_PREFIX+Instant.COLUMN_SECOND;
	public static final String COLUMN_FROM_MILLISECOND = COLUMN_FROM_PREFIX+Instant.COLUMN_MILLISECOND;
	public static final String COLUMN_FROM_WEEK_DAY_INDEX = COLUMN_FROM_PREFIX+Instant.COLUMN_DAY_IN_WEEK_INDEX;
	
	private static final String COLUMN_TO_PREFIX = "to_";
	public static final String COLUMN_TO_YEAR = COLUMN_TO_PREFIX+Instant.COLUMN_YEAR;
	public static final String COLUMN_TO_MONTH = COLUMN_TO_PREFIX+Instant.COLUMN_MONTH;
	public static final String COLUMN_TO_DAY = COLUMN_TO_PREFIX+Instant.COLUMN_DAY;
	public static final String COLUMN_TO_HOUR = COLUMN_TO_PREFIX+Instant.COLUMN_HOUR;
	public static final String COLUMN_TO_MINUTE = COLUMN_TO_PREFIX+Instant.COLUMN_MINUTE;
	public static final String COLUMN_TO_SECOND = COLUMN_TO_PREFIX+Instant.COLUMN_SECOND;
	public static final String COLUMN_TO_MILLISECOND = COLUMN_TO_PREFIX+Instant.COLUMN_MILLISECOND;
	public static final String COLUMN_TO_WEEK_DAY_INDEX = COLUMN_TO_PREFIX+Instant.COLUMN_DAY_IN_WEEK_INDEX;

	public static final String COLUMN_DISTANCE_IN_MILLISECOND = "distanceInMillisecond";
	public static final String COLUMN_DURATION_IN_MILLISECOND = "durationInMillisecond";
}