package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Attendance extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * All duration are in millisecond
	 */
	/*
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=Value.FIELD_USER,column=@Column(name="attended_numberofmillisecond_user"))
			,@AttributeOverride(name=Value.FIELD_SYSTEM,column=@Column(name="attended_numberofmillisecond_system"))
			,@AttributeOverride(name=Value.FIELD_GAP,column=@Column(name="attended_numberofmillisecond_gap"))
	})
	private Value attendedNumberOfMillisecond = new Value();
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=Value.FIELD_USER,column=@Column(name="missed_numberofmillisecond_user"))
			,@AttributeOverride(name=Value.FIELD_SYSTEM,column=@Column(name="missed_numberofmillisecond_system"))
			,@AttributeOverride(name=Value.FIELD_GAP,column=@Column(name="missed_numberofmillisecond_gap"))
	})
	private Value missedNumberOfMillisecond = new Value();
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=Value.FIELD_USER,column=@Column(name="missedjustified_numberofmillisecond_user"))
			,@AttributeOverride(name=Value.FIELD_SYSTEM,column=@Column(name="missedjustified_numberofmillisecond_system"))
			,@AttributeOverride(name=Value.FIELD_GAP,column=@Column(name="missedjustified_numberofmillisecond_gap"))
	})
	private Value missedJustifiedNumberOfMillisecond = new Value();
	*/
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=Value.FIELD_USER,column=@Column(name="rate_user"))
			,@AttributeOverride(name=Value.FIELD_SYSTEM,column=@Column(name="rate_system"))
			,@AttributeOverride(name=Value.FIELD_GAP,column=@Column(name="rate_gap"))
	})
	private Value rate = new Value();

	public Value getRate(){
		if(this.rate == null)
			this.rate = new Value();
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