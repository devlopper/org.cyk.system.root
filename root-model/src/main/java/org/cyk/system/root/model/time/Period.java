package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.value.AbstractNumberValue;
import org.cyk.system.root.model.value.AbstractValue;
import org.cyk.system.root.model.value.LongValue;

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
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=LongValue.FIELD_USER,column=@Column(name=COLUMN_NUMBER_OF_MILLISECOND_USER))
			,@AttributeOverride(name=LongValue.FIELD_SYSTEM,column=@Column(name=COLUMN_NUMBER_OF_MILLISECOND_SYSTEM))
			,@AttributeOverride(name=LongValue.FIELD_PREFERRED_PROPERTY,column=@Column(name=COLUMN_NUMBER_OF_MILLISECOND_PREFERRED_PROPERTY))
			,@AttributeOverride(name=LongValue.FIELD_GAP,column=@Column(name=COLUMN_NUMBER_OF_MILLISECOND_GAP))
	})
	private LongValue numberOfMillisecond = new LongValue();
	
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
	
	public LongValue getNumberOfMillisecond(){
		if(numberOfMillisecond==null)
			numberOfMillisecond=new LongValue();
		return numberOfMillisecond;
	}
	
	private void computeNumberOfMillisecond(){
    	if(fromDate==null || toDate==null)
    		getNumberOfMillisecond().set(null);
    	else
    		getNumberOfMillisecond().set(toDate.getTime() - fromDate.getTime());
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
	
	public static final String COLUMN_NUMBER_OF_MILLISECOND_PREFIX = FIELD_NUMBER_OF_MILLISECOND.toLowerCase()+COLUMN_NAME_WORD_SEPARATOR;
	public static final String COLUMN_NUMBER_OF_MILLISECOND_USER = FIELD_NUMBER_OF_MILLISECOND+AbstractValue.COLUMN_USER;
	public static final String COLUMN_NUMBER_OF_MILLISECOND_SYSTEM = FIELD_NUMBER_OF_MILLISECOND+AbstractValue.COLUMN_SYSTEM;
	public static final String COLUMN_NUMBER_OF_MILLISECOND_PREFERRED_PROPERTY = FIELD_NUMBER_OF_MILLISECOND+AbstractValue.COLUMN_PREFERRED_PROPERTY;
	public static final String COLUMN_NUMBER_OF_MILLISECOND_GAP = FIELD_NUMBER_OF_MILLISECOND+AbstractNumberValue.FIELD_GAP;
	
}