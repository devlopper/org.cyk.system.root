package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A period is a time interval from a start date to an end date
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Period extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP) private Date fromDate;
	
	@Temporal(TemporalType.TIMESTAMP) private Date toDate;
	
	private Long numberOfMillisecond;
	
	public Period(Date fromDate, Date toDate) {
		super();
		setFromDate(fromDate);
		setToDate(toDate);
	}
	
	public void setFromDate(Date date){
		this.fromDate = date;
		computeNumberOfMillisecond();
	}
	
	public void setToDate(Date date){
		this.toDate = date;
		computeNumberOfMillisecond();
	}
	
	private void computeNumberOfMillisecond(){
    	if(fromDate==null || toDate==null)
    		numberOfMillisecond = null;
    	else
    		numberOfMillisecond = toDate.getTime() - fromDate.getTime();
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);//+" , "+(getDuration()/DateUtils.MILLIS_PER_MINUTE)+" min";
	}

	

}