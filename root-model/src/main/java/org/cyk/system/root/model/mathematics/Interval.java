package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @Table(name="TABLE_INTERVAL") @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class Interval extends AbstractCollectionItem<IntervalCollection> implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=IntervalExtremity.FIELD_VALUE,column=@Column(name=COLUMN_LOW_VALUE))
			,@AttributeOverride(name=IntervalExtremity.FIELD_EXCLUDED,column=@Column(name=COLUMN_LOW_EXCLUDED))
	})
	private IntervalExtremity low = new IntervalExtremity();
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=IntervalExtremity.FIELD_VALUE,column=@Column(name=COLUMN_HIGH_VALUE))
			,@AttributeOverride(name=IntervalExtremity.FIELD_EXCLUDED,column=@Column(name=COLUMN_HIGH_EXCLUDED))
	})
	private IntervalExtremity high = new IntervalExtremity();
	
	/**
	 * It is the value we use When we belongs to this interval
	 */
	@Column(precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE)
	private BigDecimal value;
	
	/* color support right now */
	//private String style;
	
	{
		if(low!=null)
			low.setIsLow(Boolean.TRUE);
		if(high!=null)
			high.setIsLow(Boolean.FALSE);
	}
	
	public Interval(IntervalCollection collection, String code, String name,BigDecimal low,BigDecimal high) {
		super(collection, code, name);
		this.low = new IntervalExtremity(low);
		this.high = new IntervalExtremity(high);
	}
	
	public IntervalExtremity getLow(){
		if(low!=null)
			low.setIsLow(Boolean.TRUE);
		return low;
	}
	
	public IntervalExtremity getHigh(){
		if(high!=null)
			high.setIsLow(Boolean.FALSE);
		return high;
	}
	
	public static final String FIELD_LOW = "low";
	public static final String COLUMN_LOW_VALUE = FIELD_LOW+"_"+IntervalExtremity.FIELD_VALUE;
	public static final String COLUMN_LOW_EXCLUDED = FIELD_LOW+"_"+IntervalExtremity.FIELD_EXCLUDED;
	
	public static final String FIELD_HIGH = "high";
	public static final String COLUMN_HIGH_VALUE = FIELD_HIGH+"_"+IntervalExtremity.FIELD_VALUE;
	public static final String COLUMN_HIGH_EXCLUDED = FIELD_HIGH+"_"+IntervalExtremity.FIELD_EXCLUDED;
	
	public static final String FIELD_VALUE = "value";
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT, Boolean.TRUE.equals(low.getExcluded()) ? "]":"[",low.getValue()==null ? "<-" : low.getValue()
				,high.getValue()==null ? "->" : high.getValue(),Boolean.TRUE.equals(high.getExcluded()) ? "[":"]");
	}
	
	public static final String LOG_FORMAT = Interval.class.getSimpleName()+"(%s%s , %s%s)";
	
	public static final String EXTREMITY_SEPARATOR = Constant.CHARACTER_SPACE+Constant.CHARACTER_COMA+Constant.CHARACTER_SPACE.toString();
	public static final String FORMAT = "%s"+EXTREMITY_SEPARATOR+"%s";
	
	/*
	public Interval(IntervalManager manager) {
		this.manager = manager;
	}
	
	public Interval(IntervalManager manager,BigDecimal low, BigDecimal high,String hexColor) {
		this(manager);
		this.low = low;
		this.high = high;
		setColor(hexColor);
	}
	
	public Interval(IntervalManager manager, String name, String description,BigDecimal low, BigDecimal high, Color color) {
		super();
		this.manager = manager;
		this.name = name;
		this.description = description;
		this.low = low;
		this.high = high;
		if(color!=null)
			setColor(String.format("#%06X", (0xFFFFFF & color.getRGB())));
	}
	
	public Interval(IntervalManager manager, String name, String description,BigDecimal low, BigDecimal high) {
		this(manager,name,description,low,high,null);
	}
	
	public void setColor(String hexadecimalValue){
		style = "color:"+hexadecimalValue+";";
	}
	
	public String getColor(){
		if(style==null)
			return "";
		int colorIndex = style.indexOf("color:")+6;
		if(colorIndex==-1)
			return "";
		int comaIndex = style.indexOf(";",colorIndex);
		if(comaIndex==-1)
			return "";
		return style.substring(colorIndex, comaIndex);
	}
	*/
	/*
	public String getColorAsHexadecimal(){
		return String.format("#%06X", (0xFFFFFF & color.getRGB()));
	}
	*/
	/*
	public boolean contains(BigDecimal value,Integer scale){
		if(low==null && high==null)
			return(value==null);
		if(value==null)
			return false;
		BigDecimal correctedScale = scale==null?value:value.setScale(scale,RoundingMode.DOWN);//truncates
		if(low==null)
			if(excludeHigh)
				return correctedScale.compareTo(high)<0;
			else
				return correctedScale.compareTo(high)<=0;
		
		if(high==null)
			if(excludeLow)
				return correctedScale.compareTo(low)>0;
			else
				return correctedScale.compareTo(low)>=0;
				
		int c1 = low.compareTo(correctedScale),c2 = high.compareTo(correctedScale);
		
		boolean ok1,ok2;
		if(excludeLow)
			ok1 = c1<0;
		else
			ok1 = c1<=0;
		
		if(excludeHigh)
			ok2 = c2 >0;
		else
			ok2 = c2 >=0;	
		
		return ok1 && ok2;
	}
	
	public boolean contains(BigDecimal value){
		return contains(value,null);
	}
	*/
	/*
	public String format(String format){
		if(low==null)
			if(high==null)
				return "[-,+]";
			else
				return "<"+(excludeHigh?" ":"= ")+NumberUtils.stripTrailingZeros(high);
		else
			if(high==null)
				return ">"+(excludeLow?" ":"= ")+NumberUtils.stripTrailingZeros(low);
			else{
				if(format==null)
					format = "%s - %s";	
				return String.format(format, NumberUtils.stripTrailingZeros(low),NumberUtils.stripTrailingZeros(high));
			}
	}
	
	public String format(){
		return format(null);
	}
	*/
	

}