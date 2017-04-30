package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class WeightedValue implements Serializable {

	private static final long serialVersionUID = -4039149243809512163L;

	private BigDecimal value;

	private BigDecimal weight;
	
	private Boolean weightApplied;

	public WeightedValue(BigDecimal value, BigDecimal weight) {
		this(value,weight,Boolean.TRUE);
	}

	@Override
	public String toString() {
		return value+" "+weight;
	}
}