package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Value;
import org.cyk.utility.common.Constant;

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
	
	@AttributeOverrides(value={
			@AttributeOverride(name=Value.FIELD_USER,column=@Column(name="user_numberofmillisecond"))
			,@AttributeOverride(name=Value.FIELD_SYSTEM,column=@Column(name="system_numberofmillisecond"))
			,@AttributeOverride(name=Value.FIELD_GAP,column=@Column(name="gap_numberofmillisecond"))
	})
	private Value numberOfMillisecond = new Value();
	
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
	
	public Value getNumberOfMillisecond(){
		if(numberOfMillisecond==null)
			numberOfMillisecond=new Value();
		return numberOfMillisecond;
	}
	
	private void computeNumberOfMillisecond(){
    	if(fromDate==null || toDate==null)
    		getNumberOfMillisecond().setSystem(null);
    	else
    		getNumberOfMillisecond().setSystem(new BigDecimal(toDate.getTime() - fromDate.getTime()));
    	getNumberOfMillisecond().computeGap();
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

	public static final String FIELD_FROM_DATE = "fromDate";
	public static final String FIELD_TO_DATE = "toDate";
	public static final String FIELD_NUMBER_OF_MILLISECOND = "numberOfMillisecond";
	
	public static final String COLUMN_USER_NUMBER_OF_MILLISECOND = FIELD_NUMBER_OF_MILLISECOND+Constant.CHARACTER_UNDESCORE+Value.FIELD_USER;
	public static final String COLUMN_SYSTEM_NUMBER_OF_MILLISECOND = FIELD_NUMBER_OF_MILLISECOND+Constant.CHARACTER_UNDESCORE+Value.FIELD_SYSTEM;
	public static final String COLUMN_GAP_NUMBER_OF_MILLISECOND = FIELD_NUMBER_OF_MILLISECOND+Constant.CHARACTER_UNDESCORE+Value.FIELD_GAP;
	
}