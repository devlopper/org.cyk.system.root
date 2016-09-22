package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.utility.common.CommonUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Value extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	@Column(precision=10,scale=FLOAT_SCALE) 
	private BigDecimal user;
	
	@Column(precision=10,scale=FLOAT_SCALE)
	private BigDecimal system;
	
	@Column(precision=10,scale=FLOAT_SCALE) 
	private BigDecimal gap;
	
	public Value(BigDecimal user) {
		super();
		this.user = user;
	}
	
	public <NUMBER extends Number> NUMBER getSystemAs(Class<NUMBER> numberClass){
		return CommonUtils.getInstance().getBigDecimalAs(system, numberClass);
	}
	
	public <NUMBER extends Number> NUMBER getUserAs(Class<NUMBER> numberClass){
		return CommonUtils.getInstance().getBigDecimalAs(user, numberClass);
	}
	
	public <NUMBER extends Number> NUMBER getGapAs(Class<NUMBER> numberClass){
		return CommonUtils.getInstance().getBigDecimalAs(gap, numberClass);
	}
	
	public void computeGap(){
		gap = system==null?null:(user==null?null:system.subtract(user));
	}
	
	@Override
	public String getUiString() {
		return "U="+user+" , S="+system+" , G="+gap;
	}

	@Override
	public String toString() {
		return "U="+user+" , S="+system+" , G="+gap;
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_SYSTEM = "system";
	public static final String FIELD_GAP = "gap";
}
