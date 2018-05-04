package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="TABLE_INTERVAL") @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=IntervalCollection.class)
public class Interval extends AbstractCollectionItem<IntervalCollection> implements Serializable {
	private static final long serialVersionUID = -165832578043422718L;
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=IntervalExtremity.FIELD_VALUE,column=@Column(name=COLUMN_LOW_VALUE,precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE))
			,@AttributeOverride(name=IntervalExtremity.FIELD_EXCLUDED,column=@Column(name=COLUMN_LOW_EXCLUDED))
			,@AttributeOverride(name=IntervalExtremity.FIELD_VALUE_WITHOUT_EXCLUDED_INFORMATION,column=@Column(name=COLUMN_LOW_VALUE_WITHOUT_EXCLUDED_INFORMATION,precision=200,scale=50))
			,@AttributeOverride(name=IntervalExtremity.FIELD_IS_LOW,column=@Column(name=COLUMN_LOW_IS_LOW))
	})
	@Valid
	private IntervalExtremity low = new IntervalExtremity();
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=IntervalExtremity.FIELD_VALUE,column=@Column(name=COLUMN_HIGH_VALUE,precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE))
			,@AttributeOverride(name=IntervalExtremity.FIELD_EXCLUDED,column=@Column(name=COLUMN_HIGH_EXCLUDED))
			,@AttributeOverride(name=IntervalExtremity.FIELD_VALUE_WITHOUT_EXCLUDED_INFORMATION,column=@Column(name=COLUMN_HIGH_VALUE_WITHOUT_EXCLUDED_INFORMATION,precision=200,scale=50))
			,@AttributeOverride(name=IntervalExtremity.FIELD_IS_LOW,column=@Column(name=COLUMN_HIGH_IS_LOW))
	})
	@Valid
	private IntervalExtremity high = new IntervalExtremity();
	
	@Column private Byte numberOfDecimalAfterDot;
	
	/**
	 * It is the value we use When we belongs to this interval
	 */
	@Column(precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal value;
	
	{
		if(low!=null)
			low.setInterval(this).setIsLow(Boolean.TRUE);
		if(high!=null)
			high.setInterval(this).setIsLow(Boolean.FALSE);
	}
	/*
	public IntervalExtremity getLow(){
		if(low == null)
			low = new IntervalExtremity();
		if(low!=null)
			low.setIsLow(Boolean.TRUE);
		return low;
	}
	*/
	
	public Interval setNumberOfDecimalAfterDotFromObject(Object object){
		this.numberOfDecimalAfterDot = getNumberFromObject(Byte.class, object);
		return this;
	}
	
	public Interval setLowValueFromObject(Object object){
		getLow().setValueFromObject(object);
		return this;
	}
	
	public Interval setLowValueWithoutExcludedInformationFromObject(Object object){
		getLow().setValueWithoutExcludedInformationFromObject(object);
		return this;
	}
	
	public Interval setLowExcluded(Boolean excluded){
		getLow().setExcluded(excluded);
		return this;
	}
	/*
	public IntervalExtremity getHigh(){
		if(high == null)
			high = new IntervalExtremity();
		if(high!=null)
			high.setIsLow(Boolean.FALSE);
		return high;
	}*/
	
	public Interval setHighValueFromObject(Object object){
		getHigh().setValueFromObject(object);
		return this;
	}
	
	public Interval setHighValueWithoutExcludedInformationFromObject(Object object){
		getHigh().setValueWithoutExcludedInformationFromObject(object);
		return this;
	}
	
	public Interval setHighExcluded(Boolean excluded){
		getHigh().setExcluded(excluded);
		return this;
	}
	
	public Boolean isLowAndHighValuesWithoutExcludedInformationEquals(){
		return NumberHelper.getInstance().compare(getLow().getValueWithoutExcludedInformation(), getHigh().getValueWithoutExcludedInformation(), null, Boolean.TRUE);
	}
	
	public Boolean contains(Object value){
		value = NumberHelper.getInstance().get(value);
		return NumberHelper.getInstance().compare(getLow().getValueWithoutExcludedInformation(), (Number)value, Boolean.FALSE, Boolean.TRUE)
				&& NumberHelper.getInstance().compare(getHigh().getValueWithoutExcludedInformation(), (Number)value, Boolean.TRUE, Boolean.TRUE);
	}
	
	@Override
	public String toString() {
		return (StringHelper.getInstance().isBlank(getCode()) ? Constant.EMPTY_STRING : (getCode() + " - ") ) + (low+" "+high);
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT, Boolean.TRUE.equals(low.getExcluded()) ? "]":"[",low.getValue()==null ? "<-" : low.getValue()
				,high.getValue()==null ? "->" : high.getValue(),Boolean.TRUE.equals(high.getExcluded()) ? "[":"]");
	}
	
	public static final String FIELD_LOW = "low";
	public static final String FIELD_HIGH = "high";
	
	public static final String COLUMN_LOW_PREFIX = FIELD_LOW+COLUMN_NAME_WORD_SEPARATOR;
	public static final String COLUMN_LOW_VALUE = COLUMN_LOW_PREFIX+IntervalExtremity.FIELD_VALUE;
	public static final String COLUMN_LOW_EXCLUDED = COLUMN_LOW_PREFIX+IntervalExtremity.FIELD_EXCLUDED;
	public static final String COLUMN_LOW_VALUE_WITHOUT_EXCLUDED_INFORMATION = COLUMN_LOW_PREFIX+IntervalExtremity.FIELD_VALUE_WITHOUT_EXCLUDED_INFORMATION;
	public static final String COLUMN_LOW_IS_LOW = COLUMN_LOW_PREFIX+IntervalExtremity.FIELD_IS_LOW;
	
	public static final String COLUMN_HIGH_PREFIX = FIELD_HIGH+COLUMN_NAME_WORD_SEPARATOR;
	public static final String COLUMN_HIGH_VALUE = COLUMN_HIGH_PREFIX+IntervalExtremity.FIELD_VALUE;
	public static final String COLUMN_HIGH_EXCLUDED = COLUMN_HIGH_PREFIX+IntervalExtremity.FIELD_EXCLUDED;
	public static final String COLUMN_HIGH_VALUE_WITHOUT_EXCLUDED_INFORMATION = COLUMN_HIGH_PREFIX+IntervalExtremity.FIELD_VALUE_WITHOUT_EXCLUDED_INFORMATION;
	public static final String COLUMN_HIGH_IS_LOW = COLUMN_HIGH_PREFIX+IntervalExtremity.FIELD_IS_LOW;
	
	public static final String FIELD_VALUE = "value";
	
	public static final String LOG_FORMAT = Interval.class.getSimpleName()+"(%s%s , %s%s)";
	
	public static final String EXTREMITY_SEPARATOR = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_COMA+Constant.CHARACTER_SPACE.toString();
	public static final String FORMAT = "%s"+EXTREMITY_SEPARATOR+"%s";
	
}