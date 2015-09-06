package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
@Entity 
public class Interval  extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	@ManyToOne
	private IntervalCollection collection;
	
	private String name;
	
	@Column(length=1024 * 2)
	private String description;
	
	@Column(precision=30,scale=10)
	private BigDecimal low;
	
	@Column(precision=30,scale=10)
	private BigDecimal high;
	
	private Boolean excludeLow=Boolean.FALSE,excludeHigh=Boolean.FALSE;
	
	/* color support right now */
	private String style;
	
	public Interval() {}
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
	
	
	@Override
	public String toString() {
		return name;
	}
}