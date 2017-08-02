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
import lombok.experimental.Accessors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.helper.TimeHelper;

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
			@AttributeOverride(name=Instant.FIELD_MILLISECOND_OF_SECOND,column=@Column(name=COLUMN_FROM_MILLISECOND_OF_SECOND))
			,@AttributeOverride(name=Instant.FIELD_SECOND_OF_MINUTE,column=@Column(name=COLUMN_FROM_SECOND_OF_MINUTE))
			,@AttributeOverride(name=Instant.FIELD_MINUTE_OF_HOUR,column=@Column(name=COLUMN_FROM_MINUTE_OF_HOUR))
			,@AttributeOverride(name=Instant.FIELD_HOUR_OF_DAY,column=@Column(name=COLUMN_FROM_HOUR_OF_DAY))
			,@AttributeOverride(name=Instant.FIELD_DAY_OF_MONTH,column=@Column(name=COLUMN_FROM_DAY_OF_MONTH))
			,@AttributeOverride(name=Instant.FIELD_DAY_OF_WEEK,column=@Column(name=COLUMN_FROM_DAY_OF_WEEK))
			,@AttributeOverride(name=Instant.FIELD_MONTH_OF_YEAR,column=@Column(name=COLUMN_FROM_MONTH_OF_YEAR))
			,@AttributeOverride(name=Instant.FIELD_YEAR,column=@Column(name=COLUMN_FROM_YEAR))
	})
	private Instant from;
	
	@Embedded 
	@AttributeOverrides(value={
			@AttributeOverride(name=Instant.FIELD_MILLISECOND_OF_SECOND,column=@Column(name=COLUMN_TO_MILLISECOND_OF_SECOND))
			,@AttributeOverride(name=Instant.FIELD_SECOND_OF_MINUTE,column=@Column(name=COLUMN_TO_SECOND_OF_MINUTE))
			,@AttributeOverride(name=Instant.FIELD_MINUTE_OF_HOUR,column=@Column(name=COLUMN_TO_MINUTE_OF_HOUR))
			,@AttributeOverride(name=Instant.FIELD_HOUR_OF_DAY,column=@Column(name=COLUMN_TO_HOUR_OF_DAY))
			,@AttributeOverride(name=Instant.FIELD_DAY_OF_MONTH,column=@Column(name=COLUMN_TO_DAY_OF_MONTH))
			,@AttributeOverride(name=Instant.FIELD_DAY_OF_WEEK,column=@Column(name=COLUMN_TO_DAY_OF_WEEK))
			,@AttributeOverride(name=Instant.FIELD_MONTH_OF_YEAR,column=@Column(name=COLUMN_TO_MONTH_OF_YEAR))
			,@AttributeOverride(name=Instant.FIELD_YEAR,column=@Column(name=COLUMN_TO_YEAR))
	})
	private Instant to;
	
	@Column(name=COLUMN_DISTANCE_IN_MILLISECOND) private Long distanceInMillisecond;
	
	@Column(name=COLUMN_PORTION_IN_MILLISECOND) private Long portionInMillisecond;
	
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
	
	public TimeHelper.Instant.Interval getTimeHelperInstantInterval(){
		return new TimeHelper.Instant.Interval(from.getTimeHelperInstant(), to.getTimeHelperInstant(), distanceInMillisecond, portionInMillisecond);
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
	public static final String FIELD_PORTTION_IN_MILLISECOND = "portionInMillisecond";
	
	private static final String COLUMN_FROM_PREFIX = "from_";
	public static final String COLUMN_FROM_YEAR = COLUMN_FROM_PREFIX+Instant.COLUMN_YEAR;
	public static final String COLUMN_FROM_MONTH_OF_YEAR = COLUMN_FROM_PREFIX+Instant.COLUMN_MONTH_OF_YEAR;
	public static final String COLUMN_FROM_DAY_OF_MONTH = COLUMN_FROM_PREFIX+Instant.COLUMN_DAY_OF_MONTH;
	public static final String COLUMN_FROM_HOUR_OF_DAY = COLUMN_FROM_PREFIX+Instant.COLUMN_HOUR_OF_DAY;
	public static final String COLUMN_FROM_MINUTE_OF_HOUR = COLUMN_FROM_PREFIX+Instant.COLUMN_MINUTE_OF_HOUR;
	public static final String COLUMN_FROM_SECOND_OF_MINUTE = COLUMN_FROM_PREFIX+Instant.COLUMN_SECOND_OF_MINUTE;
	public static final String COLUMN_FROM_MILLISECOND_OF_SECOND = COLUMN_FROM_PREFIX+Instant.COLUMN_MILLISECOND_OF_SECOND;
	public static final String COLUMN_FROM_DAY_OF_WEEK = COLUMN_FROM_PREFIX+Instant.COLUMN_DAY_OF_WEEK;
	
	private static final String COLUMN_TO_PREFIX = "to_";
	public static final String COLUMN_TO_YEAR = COLUMN_TO_PREFIX+Instant.COLUMN_YEAR;
	public static final String COLUMN_TO_MONTH_OF_YEAR = COLUMN_TO_PREFIX+Instant.COLUMN_MONTH_OF_YEAR;
	public static final String COLUMN_TO_DAY_OF_MONTH = COLUMN_TO_PREFIX+Instant.COLUMN_DAY_OF_MONTH;
	public static final String COLUMN_TO_HOUR_OF_DAY = COLUMN_TO_PREFIX+Instant.COLUMN_HOUR_OF_DAY;
	public static final String COLUMN_TO_MINUTE_OF_HOUR = COLUMN_TO_PREFIX+Instant.COLUMN_MINUTE_OF_HOUR;
	public static final String COLUMN_TO_SECOND_OF_MINUTE = COLUMN_TO_PREFIX+Instant.COLUMN_SECOND_OF_MINUTE;
	public static final String COLUMN_TO_MILLISECOND_OF_SECOND = COLUMN_TO_PREFIX+Instant.COLUMN_MILLISECOND_OF_SECOND;
	public static final String COLUMN_TO_DAY_OF_WEEK = COLUMN_TO_PREFIX+Instant.COLUMN_DAY_OF_WEEK;

	public static final String COLUMN_DISTANCE_IN_MILLISECOND = "distanceInMillisecond";
	public static final String COLUMN_PORTION_IN_MILLISECOND = "portionInMillisecond";
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		private Instant.SearchCriteria from = new Instant.SearchCriteria();
		private Instant.SearchCriteria to = new Instant.SearchCriteria();
		
		@Override
		public void set(String value) {
			
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
		public void set(){
			
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}