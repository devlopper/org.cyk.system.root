package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	public static Date DATE_LOWEST;
	public static Date DATE_HIGHEST;
	//private String timeZone;
	
	static{
		try {
			DATE_LOWEST = DateUtils.parseDate("1/1/1", "dd/MM/yyy");
			DATE_HIGHEST = DateUtils.parseDate("1/1/10000", "dd/MM/yyy");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public Boolean contains(Date date){
		return fromDate.getTime()<= date.getTime() && date.getTime() <= toDate.getTime();
	}
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return DATE_FORMAT.format(fromDate)+" "+DATE_FORMAT.format(toDate);//+" , "+(getDuration()/DateUtils.MILLIS_PER_MINUTE)+" min";
	}

}