package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.model.AbstractModelElement;

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
	//TODO name suffix should be changed to NumberOfMillisecond
	private Long attendedDuration;
	
	private Long missedDuration;
	
	private Long missedDurationJustified;
	
	private BigDecimal rate;

	public void addAttendedDuration(Long amount){
		this.attendedDuration += amount;
	}
	
	public void addMissedDuration(Long amount){
		this.missedDuration += amount;
	}
	
	public void addMissedDurationJustified(Long amount){
		this.missedDurationJustified += amount;
	}
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return ((attendedDuration==null?0l:attendedDuration)/DateUtils.MILLIS_PER_MINUTE)+" min , "
				+((missedDuration==null?0l:missedDuration)/DateUtils.MILLIS_PER_MINUTE)+" min , "+((missedDurationJustified==null?0l:missedDurationJustified)/DateUtils.MILLIS_PER_MINUTE)+" min";
	}

}