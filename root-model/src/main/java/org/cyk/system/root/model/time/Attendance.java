package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.cyk.system.root.model.value.BigDecimalValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Attendance extends org.cyk.utility.common.model.identifiable.Embeddable.BaseClass.JavaPersistenceEmbeddable implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * All duration are in millisecond
	 */
	
	@Embedded
	@AttributeOverrides(value={
		@AttributeOverride(name=BigDecimalValue.FIELD_USER,column=@Column(name="rate_user"))
		,@AttributeOverride(name=BigDecimalValue.FIELD_SYSTEM,column=@Column(name="rate_system"))
		,@AttributeOverride(name=BigDecimalValue.FIELD_GAP,column=@Column(name="rate_gap"))
		,@AttributeOverride(name=BigDecimalValue.FIELD_PREFERRED_PROPERTY,column=@Column(name="rate_preferred_property"))
	})
	private BigDecimalValue rate = new BigDecimalValue();

	public BigDecimalValue getRate(){
		if(this.rate == null)
			this.rate = new BigDecimalValue();
		return this.rate;
	}
	
	@Override
	public String getUiString() {
		return toString();
	}
	/*
	public Value getAttendedNumberOfMillisecond(){
		if(this.attendedNumberOfMillisecond == null)
			this.attendedNumberOfMillisecond = new Value();
		return this.attendedNumberOfMillisecond;
	}
	
	public Value getMissedNumberOfMillisecond(){
		if(this.missedNumberOfMillisecond == null)
			this.missedNumberOfMillisecond = new Value();
		return this.missedNumberOfMillisecond;
	}
	
	public Value getMissedJustifiedNumberOfMillisecond(){
		if(this.missedJustifiedNumberOfMillisecond == null)
			this.missedJustifiedNumberOfMillisecond = new Value();
		return this.missedJustifiedNumberOfMillisecond;
	}
	*/
	
	public static final String ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_ATTENDED = "ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_ATTENDED";
	public static final String ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_MISSED = "ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_MISSED";
	public static final String ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_MISSED_JUSTIFIED = "ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_MISSED_JUSTIFIED";
	public static final String ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_SUSPENDED = "ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_SUSPENDED";
	public static final String ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_DETENTION = "ATTENDANCE_METRIC_VALUE_NUMBER_OF_MILLISECOND_DETENTION";

}