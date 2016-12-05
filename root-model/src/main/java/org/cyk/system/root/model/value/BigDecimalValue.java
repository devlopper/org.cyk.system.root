package org.cyk.system.root.model.value;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

import org.cyk.system.root.model.value.AbstractNumberValue;
import org.cyk.utility.common.CommonUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor 
public class BigDecimalValue extends AbstractNumberValue<BigDecimal> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	public BigDecimalValue(BigDecimal user) {
		super(user);
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
	protected BigDecimal getZeroValue() {
		return BigDecimal.ZERO;
	}

	@Override
	protected BigDecimal __add__(BigDecimal value1, BigDecimal value2) {
		return value1.add(value2);
	}

	@Override
	protected BigDecimal __subtract__(BigDecimal value1, BigDecimal value2) {
		return value1.subtract(value2);
	}
	
}
