package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;

/**
 * A period is a time interval from a start date to an end date
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Period extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;

	//private String timeZone;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable=false)
	@Input
	@InputCalendar
	protected Date fromDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable=false)
	@Input
	@InputCalendar
	protected Date toDate;
	
	public Long getDuration(){
    	return toDate.getTime() - fromDate.getTime();
    }
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return fromDate+" "+toDate+" , "+(getDuration()/DateUtils.MILLIS_PER_MINUTE)+" min";
	}

}