package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

import org.cyk.utility.common.CommonUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor 
public class BigDecimalValue extends AbstractValue<BigDecimal> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	public BigDecimalValue(BigDecimal user) {
		super(user);
	}
	/*
	public BigDecimalValue set(BigDecimal value,Boolean isUser){
		return set(value,isUser);
	}
	
	public BigDecimalValue set(BigDecimal value){
		return set(value,Boolean.TRUE);
	}*/
	
	public void addUser(BigDecimal value){
		if(value!=null){
			if(this.user == null)
				this.user = BigDecimal.ZERO;
			this.user = this.user.add(value);
		}
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
	
	@Override
	public void computeGap(){
		gap = system==null?null:(user==null?null:system.subtract(user));
	}
	
}
